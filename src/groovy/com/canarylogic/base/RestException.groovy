package com.canarylogic.base

class RestException {
	def additionalInfo="Not Available"
	public RestException(String message, String additionaInfo)
	{
		super(message)
		this.additionalInfo = additionaInfo
	}
}
