
package bitencoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Protocol Writer
 * provides helper methods for writing various data types 
 * @author wherrera
 */
public class ProtocolWriter {
    static final char[] HEX = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    private final ByteArrayOutputStream data = new ByteArrayOutputStream();

    public static byte HexToByte(char c)
    {
        for (int i = 0; i < HEX.length; i++)
        {
            char h = HEX[i];
            if (c == h)
                return (byte)i;
        }
        return 0;
    }
    
    public static byte AsciiToByte(String text, int index)
    {
        if (text.length() - index < 0)
            return 0;

        String lower = text.toLowerCase();

        char high = lower.charAt(index + 0);
        char low = lower.charAt(index + 1);
        byte a = HexToByte(high);
        byte b = HexToByte(low);

        byte ret = (byte)((a << 4) | b);

        return ret;
    }
    
    public byte[] getData(){        
        return data.toByteArray();
    }
    
    public static char parseHexString (String str) {
        char ch = 0;
        if(str.length() == 2) {
            byte a = AsciiToByte(str,0);
            ch = (char)a;
        } else if (str.length() == 4) {
            byte a = AsciiToByte(str,0);
            byte b = AsciiToByte(str,2);
            ch = (char)((a << 8) | b);
        }
        return ch;
    }
    
    public void writeByte(byte b){
        data.write(b);
    }
    
    public void writeHexString (String hex) {
        writeChar(parseHexString(hex));
    }
    
    public void writeChar(char ch) {
        int a = (0x00ff & ch);
        int b = (0xff00 & ch) >> 8;
        writeByte((byte)a);
        writeByte((byte)b);
    }
    public void writeBytes(byte[] b){
        try{
            if(b == null || b.length == 0){
                return;
            }
            data.write(b);
        }catch(IOException th){
        }
    }
    public void writeFloat(float i){
        writeInt( Float.floatToRawIntBits(i) );
    }
    public void writeInt(int i){
        int a = (0x000000ff & i);
        int b = (0x0000ff00 & i) >> 8;
        int c = (0x00ff0000 & i) >> 16;
        int d = (0xff000000 & i) >> 24;
        writeByte((byte)a);
        writeByte((byte)b);
        writeByte((byte)c);
        writeByte((byte)d);
    }
    public void writeLong(long i){
        byte a = (byte)( 0x00000000000000ffL & i);
        byte b = (byte)((0x000000000000ff00L & i) >> 8);
        byte c = (byte)((0x0000000000ff0000L & i) >> 16);
        byte d = (byte)((0x00000000ff000000L & i) >> 24);        
        byte e = (byte)((0x000000ff00000000L & i) >> 32);
        byte f = (byte)((0x0000ff0000000000L & i) >> 40);
        byte g = (byte)((0x00ff000000000000L & i) >> 48);
        byte h = (byte)((0xff00000000000000L & i) >> 56);        
        writeByte((byte)a);
        writeByte((byte)b);
        writeByte((byte)c);
        writeByte((byte)d);
        writeByte((byte)e);
        writeByte((byte)f);
        writeByte((byte)g);
        writeByte((byte)h);
    }
    public void writeString(String[] array){
        for(int i=0;i<array.length;i++){
            writeString(array[i]);
        }
    }
    public void writeString(String str){
        byte[] string = str.getBytes();
        writeByte( (byte)string.length );
        writeBytes(string);
    }
    public void writeBool(boolean value){
        if(value){
            writeByte((byte)1);
        }
        else{
            writeByte((byte)0);
        }
    }
}
