package com.prgguru.example;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WebService {
    private static String NAMESPACE = "http://tempuri.org/";
    private static String URL = "http://192.168.0.24:45455/Service1.asmx?WSDL";//Make sure you changed IP address
    private static String SOAP_ACTION = "http://tempuri.org/";

    public static boolean invokeLoginWS(String userName, String passWord, String webMethName) {
        boolean loginStatus = false;
        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        PropertyInfo unamePI = new PropertyInfo();
        unamePI.setName("_userName");
        unamePI.setValue(userName);
        unamePI.setType(String.class);
        request.addProperty(unamePI);

        PropertyInfo passPI = new PropertyInfo();
        passPI.setName("_passWord");
        passPI.setValue(passWord);
        passPI.setType(String.class);
        request.addProperty(passPI);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            loginStatus = Boolean.parseBoolean(response.toString());
        } catch (Exception e) {
            CheckDNLoginActivity.errored = true;
            e.printStackTrace();
        }
        return loginStatus;
    }
}
