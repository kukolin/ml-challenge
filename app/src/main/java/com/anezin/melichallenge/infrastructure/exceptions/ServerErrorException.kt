package com.anezin.melichallenge.infrastructure.exceptions

class ServerErrorException(override val message: String) : Exception(message)