package com.ngfds.wsserver.controller.message;

import com.ngfds.wsserver.service.message.MessageService;
import org.bson.Document;
import org.json.JSONObject;

public class MessageController {

    private final MessageService messageService;

    public MessageController() {
        messageService =  new MessageService();
    }

    public JSONObject createMessage(JSONObject payload){

        return messageService.createMessage(payload);

    }

}
