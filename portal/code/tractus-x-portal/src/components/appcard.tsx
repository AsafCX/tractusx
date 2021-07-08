// Copyright (c) 2021 Microsoft
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import * as React from 'react';
import { observer } from 'mobx-react';
import { Application } from '../data/application';
import Ratings from './ratings';
import { RouteComponentProps, withRouter } from 'react-router';

interface IProp extends RouteComponentProps {
  app: Application, onClick?: Function,
  wide?: boolean
  uploads?: boolean
  hideRating?: boolean
}

@observer
class AppCard extends React.Component<IProp> {

  cardClick(): void {
    if (this.props.onClick) {
      this.props.onClick(this.props.app);
    } else if (this.props.history.length > 1 && this.props.history.location.pathname.indexOf('/appdetail/') >= 0) {
      this.props.history.replace(`/home/appdetail/${this.props.app.id}`);
    } else {
      this.props.history.push(`/home/appdetail/${this.props.app.id}`);
    }
  }

  public render() {
    const a = this.props.app;
    if (!this.props.wide) {
      return (
        <div className='h250 m5 br4 bsAppStore bgwhite minw200 maxw200 cpointer' onClick={() => this.cardClick()}>
          {!this.props.hideRating && <Ratings className='mt80 ml15 mb5' app={a} />}
          {this.props.hideRating && <div className='mt80 ml15 mb20'>&nbsp;</div>}
          <div className='ml15 fs14 fggrey mb5'>{a.companyName}</div>
          <div className='ml15 bold fs14 minh40'>{a.title}</div>
          <div className='h50 mt20 tal ml15'>
            <div className='fglgreen bold fs14'>{this.props.uploads ? 'UPLOAD' : a.purchase}</div>
            {!this.props.uploads && <div className='fsxs fgb5'>{a.usage}</div>}
          </div>
        </div>
      );
    } else {
      return (
        <div className='w340 minh160 maxh160 m5 br4 bsAppStore bgwhite cpointer' onClick={() => this.cardClick()}>
          <Ratings className='mt20 ml15 mb5' app={a} />
          <div className='ml15 fs14 fggrey mb5 mt20'>{a.companyName}</div>
          <div className='df'>
            <div className='ml15 bold fs14 minh40'>{a.title}</div>
            <div className='flex1' />
            <div className='h50 mt20 mr20'>
              <div className='fglgreen bold fs14'>{this.props.uploads ? 'UPLOAD' : a.purchase}</div>
              {!this.props.uploads && <div className='fsxs fgb5'>{a.usage}</div>}
            </div>
          </div>
        </div>
      );
    }
  }
}

export default withRouter(AppCard);
