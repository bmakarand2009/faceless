package com.canarylogic.base

class RestException extends Exception {
	def additionalInfo="Not Available"
	public RestException(String message, String additionaInfo)
	{
		super(message)
		this.additionalInfo = additionaInfo
	}
}
