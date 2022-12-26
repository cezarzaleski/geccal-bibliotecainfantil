package com.geccal.bibliotecainfantil.core.domain.exception

import com.geccal.bibliotecainfantil.core.domain.validator.handler.NotificationHandler

class NotificationException(aMessage: String?, notification: NotificationHandler) :
    DomainException(aMessage, notification.getErrors())