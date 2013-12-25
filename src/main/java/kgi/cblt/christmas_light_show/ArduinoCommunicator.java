package kgi.cblt.christmas_light_show;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

/**
 * Created by IntelliJ IDEA.
 * User: max
 * Date: 12/22/10
 * Time: 8:38 PM
 */
public class ArduinoCommunicator extends Thread implements SerialPortEventListener {

    public StateSource stateSource;
    public InputStream input;
    public OutputStream output;

    public Object semafor = new Object();
    public ArduinoReader r;

    public static void main(String[] args) {
        try {
            ArduinoCommunicator comm = new ArduinoCommunicator(new StateSource() {
                int state = 16383;

                public int getState() {
                    return state;

                }
            });
            comm.setDaemon(false);
            comm.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void serialEvent(SerialPortEvent serialPortEvent) {
        System.out.println("serialPortEvent = " + serialPortEvent.getEventType());
    }


    public ArduinoCommunicator(StateSource stateSource) throws Exception {
        this.stateSource = stateSource;

        Enumeration ports = CommPortIdentifier.getPortIdentifiers();
        String portIdentifier = "";
        while (ports.hasMoreElements()) {
            CommPortIdentifier commPortIdentifier = (CommPortIdentifier) ports.nextElement();
            String commPortIdentifierName = commPortIdentifier.getName();
//            if (commPortIdentifierName.startsWith("/dev/tty.usbmo")) {
            if (commPortIdentifierName.startsWith("/dev/tty.HC")) {
                portIdentifier = commPortIdentifierName;
            }
            System.out.println("ports.nextElement() = " + commPortIdentifierName + "/" + commPortIdentifier.isCurrentlyOwned());

        }
        System.out.println("Using port::" + portIdentifier);
        CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(portIdentifier);


        SerialPort port = (SerialPort) portId.open("serial talk", 4000);
        //port.addEventListener(this);
        // port.notifyOnDataAvailable(true);
        input = port.getInputStream();
        output = port.getOutputStream();
        r = new ArduinoReader(input);
        r.setDaemon(false);
        r.start();
        //port.setSerialPortParams(115200,
        port.setSerialPortParams(9600,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);

    }

    @Override
    public void run() {
        while (true) {
            try {
                output.write(makeArduinoCommand(stateSource.getState()));
                output.flush();
                synchronized (semafor) {
                    semafor.wait(2000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Thread.yield();
        }
    }

    String format = String.format("%%0%dd", 5);


    private byte[] makeArduinoCommand(int state) {

        String s = "s" + String.format(format, state);
//        System.out.println("arduino command = " + s);
        byte[] bytes = s.getBytes();
//        for (byte aByte : bytes) {
//            System.out.print(aByte + " ");
//        }
//        System.out.println();
        return bytes;
    }


    class ArduinoReader extends Thread {

        private InputStream input;

        public ArduinoReader(InputStream input) {
            this.input = input;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    if (input.available() > 0) {
                        char r = (char) input.read();
                        //System.out.println(r + " = " + (int)r);
                        if ('z' == r) {
                            synchronized (semafor) {
                                semafor.notifyAll();
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
