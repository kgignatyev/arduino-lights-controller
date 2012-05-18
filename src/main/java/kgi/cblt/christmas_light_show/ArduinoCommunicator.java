package kgi.cblt.christmas_light_show;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

/**
 * Created by IntelliJ IDEA.
 * User: max
 * Date: 12/22/10
 * Time: 8:38 PM
 */
public class ArduinoCommunicator extends Thread {

    public StateSource stateSource;
    public InputStream input;
    public OutputStream output;

    public Object semafor = new Object();


    public static void main(String[] args) {
        try {
            ArduinoCommunicator comm = new ArduinoCommunicator(new StateSource() {
                int state = 128;

                public int getState() {
                    return state;

                }
            });
            comm.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  class ArduinoReader extends Thread {
        public ArduinoReader(InputStream input) {
            this.input = input;
        }

        InputStream input;

        @Override
        public void run() {
            try {
                while (true) {
                    while (input.available() > 0) {
                        char read = (char) (input.read());
                        System.out.print(read);
                        if( 'z' == read ){
                           // synchronized (semafor){
                              semafor.notifyAll();
                           // }
                        }
                    }
                    Thread.yield();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ArduinoCommunicator(StateSource stateSource) throws Exception {
        this.stateSource = stateSource;
        ArduinoReader r;

        Enumeration ports = CommPortIdentifier.getPortIdentifiers();
        String portIdentifier =  "/dev/tty.usbmodem1d11";
        while (ports.hasMoreElements()) {
            CommPortIdentifier commPortIdentifier = (CommPortIdentifier) ports.nextElement();
            String commPortIdentifierName = commPortIdentifier.getName();
            if( commPortIdentifierName.startsWith("/dev/tty.usbmo")) {
               portIdentifier = commPortIdentifierName;
            }
            System.out.println("ports.nextElement() = " + commPortIdentifierName + "/" + commPortIdentifier.isCurrentlyOwned());

        }
        System.out.println("Using port::" + portIdentifier);
        CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(portIdentifier);


        SerialPort port = (SerialPort) portId.open("serial talk", 4000);
        input = port.getInputStream();
        output = port.getOutputStream();
        r = new ArduinoReader(input);
        r.setDaemon(false);
        //r.start();
        port.setSerialPortParams(115200,
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
                boolean stop =  false;
                while (!stop) {
                    while (input.available() > 0) {
                        char read = (char) (input.read());
                        System.out.print(read);
                        if( 'z' != read ){
                           stop = true;
                        }
                    }
                }
                Thread.yield();

            } catch (Exception e) {
                e.printStackTrace();
            }
        Thread.yield();
        }
    }

    String format = String.format("%%0%dd", 5);


    private byte[] makeArduinoCommand(int state) {

        String s = "s" + String.format(format, state);
        System.out.println("arduino command = " + s);
        byte[] bytes = s.getBytes();
        for (byte aByte : bytes) {
            System.out.print(aByte + " ");
        }
        System.out.println();
        return bytes;
    }
}
