# coding: utf-8

from __future__ import absolute_import
from datetime import date, datetime  # noqa: F401

from typing import List, Dict  # noqa: F401

from oem_resolver.models.base_model_ import Model
from oem_resolver import util


class ResolveBulkRequest(Model):
    """NOTE: This class is auto generated by the swagger code generator program.

    Do not edit the class manually.
    """
    def __init__(self, identifiers: List[str]=None):  # noqa: E501
        """ResolveBulkRequest - a model defined in Swagger

        :param identifiers: The identifiers of this ResolveBulkRequest.  # noqa: E501
        :type identifiers: List[str]
        """
        self.swagger_types = {
            'identifiers': List[str]
        }

        self.attribute_map = {
            'identifiers': 'identifiers'
        }
        self._identifiers = identifiers

    @classmethod
    def from_dict(cls, dikt) -> 'ResolveBulkRequest':
        """Returns the dict as a model

        :param dikt: A dict.
        :type: dict
        :return: The resolveBulkRequest of this ResolveBulkRequest.  # noqa: E501
        :rtype: ResolveBulkRequest
        """
        return util.deserialize_model(dikt, cls)

    @property
    def identifiers(self) -> List[str]:
        """Gets the identifiers of this ResolveBulkRequest.

        List of VINs to be resolved to converter URL  # noqa: E501

        :return: The identifiers of this ResolveBulkRequest.
        :rtype: List[str]
        """
        return self._identifiers

    @identifiers.setter
    def identifiers(self, identifiers: List[str]):
        """Sets the identifiers of this ResolveBulkRequest.

        List of VINs to be resolved to converter URL  # noqa: E501

        :param identifiers: The identifiers of this ResolveBulkRequest.
        :type identifiers: List[str]
        """
        if identifiers is None:
            raise ValueError("Invalid value for `identifiers`, must not be `None`")  # noqa: E501

        self._identifiers = identifiers
