/*
 *  Copyright (c) 2020, 2021 Microsoft Corporation
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Microsoft Corporation - initial API and implementation
 *
 */

package org.eclipse.dataspaceconnector.transfer.core.transfer;

import lombok.Builder;
import org.eclipse.dataspaceconnector.spi.monitor.Monitor;
import org.eclipse.dataspaceconnector.spi.transfer.TransferProcessManager;
import org.eclipse.dataspaceconnector.spi.transfer.store.TransferProcessStore;
import org.eclipse.dataspaceconnector.spi.types.domain.transfer.TransferProcess;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.eclipse.dataspaceconnector.spi.types.domain.transfer.TransferProcessStates.IN_PROGRESS;

public class TransferProcessWatchdog {
    private final Monitor monitor;
    private final TransferProcessManager transferProcessManager;
    private TransferProcessStore transferProcessStore;
    private final int batchSize;
    private final long delayInSeconds;
    private final long stateTimeout;

    private ScheduledExecutorService executor;

    @Builder
    private TransferProcessWatchdog(Monitor monitor, TransferProcessManager transferProcessManager) {
        this.monitor = monitor;
        this.transferProcessManager = transferProcessManager;
        // TODO: make props configurable
        this.batchSize = 5;
        this.delayInSeconds = 1;
        this.stateTimeout = 20;
    }

    public void start(TransferProcessStore processStore) {
        transferProcessStore = processStore;
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(this::run, 0, delayInSeconds, TimeUnit.SECONDS);
    }

    public void stop() {
        if (executor != null) {
            executor.shutdownNow();
        }
    }

    private void run() {
        List<TransferProcess> transferProcesses = transferProcessStore.nextForState(IN_PROGRESS.code(), batchSize);
        transferProcesses.stream()
                .filter(p -> p.getStateTimestamp() > stateTimeout)
                .forEach(p -> {
                    monitor.info("Timeout for process " + p.getId());
                    transferProcessManager.cancelTransferProcess(p.getId());
                });
    }
}
