package com.io2020.PodzielSieKsiazka.schemas;

public class Message {
    private String text; // message body
    private MemberData memberData; // data of the user that sent this message

    public Message(String text, MemberData memberData) {
        this.text = text;
        this.memberData = memberData;
    }

    public String getText() {
        return text;
    }

    public MemberData getMemberData() {
        return memberData;
    }

}
