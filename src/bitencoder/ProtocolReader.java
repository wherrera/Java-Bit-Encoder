package bitencoder;

import java.io.UnsupportedEncodingException;

/**
 * Protocol Reader
 * provides helper methods for reading various data types 
 * @author wherrera
 */
public class ProtocolReader {
    private byte[] data;
    private int pos = 0;
    public ProtocolReader(byte[] data){
        this.data = data;
    }
    public int readByte(){
        if(pos > getLength()-1){
            return -1;
        }
        int r = data[pos];
        pos++;
        return r;
    }
    public boolean readBool(){
        int value = readByte();
        if(value == 1){
            return true;
        }
        else{
            return false;
        }
    }
    public byte[] readRemainingData(){
        if(getPosition() >= getLength()){
            return new byte[]{};
        }
        return readBytes( getLength() - getPosition() );
    }
    public byte[] readBytes(int count){
        if(count >getLength()-pos || count < 1){
            return null;
        }
        byte[] buffer = new byte[count];
        System.arraycopy(data, pos, buffer, 0, count);
        pos+=count;
        return buffer;
    }
    public int readInt()
    {
        if(getLength()-pos < 4){
            return -1;
        }        
        int a = 0x000000ff & (((int)data[pos+0]));
        int b = 0x0000ff00 & (((int)data[pos+1])<<8);
        int c = 0x00ff0000 & (((int)data[pos+2])<<16);
        int d = 0xff000000 & (((int)data[pos+3])<<24);
        pos+=4;
        return a|b|c|d;
    }
    public long readLong()
    {
        if(getLength()-pos < 8){
            return -1L;
        }
        long a = 0x00000000000000ffL & (((long)data[pos+0]));
        long b = 0x000000000000ff00L & (((long)data[pos+1])<<8);
        long c = 0x0000000000ff0000L & (((long)data[pos+2])<<16);
        long d = 0x00000000ff000000L & (((long)data[pos+3])<<24);
        long e = 0x000000ff00000000L & (((long)data[pos+4])<<32);
        long f = 0x0000ff0000000000L & (((long)data[pos+5])<<40);
        long g = 0x00ff000000000000L & (((long)data[pos+6])<<48);
        long h = 0xff00000000000000L & (((long)data[pos+7])<<56);        
        pos+=8;
        return a|b|c|d|e|f|g|h;
    }
    public float readFloat()
    {
        int i = readInt();
        return Float.intBitsToFloat(i);
    }
    public String readString(){
        try{
            int size = readByte();
            if(size == -1 || size > getLength()-pos){
                return null;
            }
            byte[] buffer = readBytes(size);
            if(buffer == null){
                return null;
            }
            return new String(buffer,"UTF-8");
        }catch(UnsupportedEncodingException e){
            return null;
        }
    }
    public int getLength(){
        return data.length;
    }
    public void rewind(){
        pos = 0;
    }
    public int getPosition(){
        return pos;
    }
}
