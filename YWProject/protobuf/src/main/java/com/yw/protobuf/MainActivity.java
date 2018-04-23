package com.yw.protobuf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.protobuf.InvalidProtocolBufferException;
import com.yw.protobuf.proto.AddressBookProtos;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SocketTest socketTest = new SocketTest();
        socketTest.start();

    }

    void protoc() {
        AddressBookProtos.Person.PhoneNumber.Builder builder = AddressBookProtos.Person.PhoneNumber.newBuilder().setNumber("10086");
        AddressBookProtos.Person.Builder yw = AddressBookProtos.Person.newBuilder().setName("YW").setId(1).addPhones(builder);
        AddressBookProtos.AddressBook addressBook = AddressBookProtos.AddressBook.newBuilder().addPeople(yw.build()).build();

        long l = System.currentTimeMillis();
        byte[] bytes = addressBook.toByteArray();
        System.out.println("protobuf 序列化时间：" + (System.currentTimeMillis() - l));
        System.out.println("protobuf 序列化数据大小：" + bytes.length);

        //反序列化
        l = System.currentTimeMillis();
        try {
            AddressBookProtos.AddressBook addressBook1 = AddressBookProtos.AddressBook.parseFrom(bytes);
            System.out.println("protobuf 反序列化耗时：" + (System.currentTimeMillis() - l));
            System.out.println("toString: " + addressBook1.toString());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}
