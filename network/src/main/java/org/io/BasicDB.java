package org.io;


import java.io.*;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * 利用RandomAccessFile实现一个简单的键值数据库，BasicDB
 * 功能：按键保存、查找、删除，数据可以持久化保存到文件中
 * BasicDB将元数据如索引信息保存在内存，值的数据保存到本地文件上。
 * @author cy
 */
public class BasicDB {
    private static final int MAX_DATA_LENGTH=1020;

    private static final byte[] ZERO_BYTES=new byte[MAX_DATA_LENGTH];

    private static final String DATA_SUFFIX=".data";
    private static final String META_SUFFIX=".meta";

    Map<String,Long> indexMap;
    Queue<Long> gaps;

    RandomAccessFile db;
    File metaFile;
    /**
     *  构造方法
     * @param path 数据库文件所在的目录，必须存在
     * @param name 数据库名称，BasicDB使用以name开头的两个文件，.meta存储元数据；.data，存储键值对。
     *             如果这两个文件不存在，创建新的数据库
     * @throws IOException 找不到文件
     */
    public BasicDB(String path,String name) throws IOException{
        File dataFile=new File(path+name+DATA_SUFFIX);
        metaFile=new File(path+name+META_SUFFIX);

        db=new RandomAccessFile(dataFile,"rw");
        if(metaFile.exists()){
            loadMeta();
        }else {
            indexMap=new HashMap<>();
            gaps=new ArrayDeque<>();
        }
    }

    /**
     * 保存键值对
     * @param key 键
     * @param value 值
     */
    public void put(String key,byte[] value) throws IOException{
        Long index=indexMap.get(key);
        if (index==null){
            index=nextAvailablePos();
            indexMap.put(key,index);
        }
        writeData(index,value);
    }

    private long nextAvailablePos() throws IOException{
        if(!gaps.isEmpty()){
            return gaps.poll();
        }else {
            return db.length();
        }
    }

    private void writeData(long pos,byte[] data) throws IOException{
        if(data.length>MAX_DATA_LENGTH){
            throw new IllegalArgumentException("maximum allowed length is"+MAX_DATA_LENGTH+",data length is"+data.length);
        }
        db.seek(pos);
        db.writeInt(data.length);
        db.write(data);
        db.write(ZERO_BYTES,0,MAX_DATA_LENGTH-data.length);
    }

    public byte[] get(String key) throws IOException{
        Long index = indexMap.get(key);
        if(index!=null){
            return getData(index);
        }
        return null;
    }

    private byte[] getData(long pos) throws IOException{
        db.seek(pos);
        int length=db.readInt();
        byte[] data=new byte[length];
        db.readFully(data);
        return data;
    }

    public void remove(String key){
        Long index=indexMap.remove(key);
        if(index!=null){
            gaps.offer(index);
        }
    }

    public void flush() throws  IOException{
        saveMeta();
        db.getFD().sync();
    }

    private void saveMeta() throws IOException{
        DataOutputStream out=new DataOutputStream(new BufferedOutputStream(new FileOutputStream(metaFile)));
        try{
            saveIndex(out);
            saveGaps(out);
        }finally {
            out.close();
        }
    }

    private void saveIndex(DataOutputStream out) throws IOException{
        out.writeInt(indexMap.size());
        for (Map.Entry<String,Long> entry:indexMap.entrySet()){
            out.writeUTF(entry.getKey());
            out.writeLong(entry.getValue());
        }
    }
    private void saveGaps(DataOutputStream out) throws IOException{
        out.writeInt(gaps.size());
        for(Long pos:gaps){
            out.writeLong(pos);
        }
    }

    private void loadMeta() throws IOException{
        DataInputStream in=new DataInputStream(new BufferedInputStream(new FileInputStream(metaFile)));
        try{
            loadIndex(in);
            loadGaps(in);
        }finally {
            in.close();
        }
    }

    private void loadIndex(DataInputStream in) throws IOException{
        int size=in.readInt();
        indexMap=new HashMap<String,Long>((int) (size/0.75f)+1,0.75f);
        for (int i = 0; i < size; i++) {
            String key=in.readUTF();
            long index=in.readLong();
            indexMap.put(key,index);
        }
    }

    private void loadGaps(DataInputStream in) throws IOException{
        int size=in.readInt();
        gaps=new ArrayDeque<>(size);
        for (int i = 0; i < size; i++) {
            long index=in.readLong();
            gaps.add(index);
        }
    }

    public void close() throws IOException{
        flush();
        db.close();
    }

    /**
     * 将Student转换成字节数组
     */
    public static byte[] toBytes(Student student) throws IOException{
        ByteArrayOutputStream bout=new ByteArrayOutputStream();
        DataOutputStream dout=new DataOutputStream(bout);
        dout.writeUTF(student.getName());
        dout.writeInt(student.getAge());
        dout.writeInt(student.getScore());
        return bout.toByteArray();
    }

    public static void saveStudents(BasicDB db,Map<String,Student> students) throws IOException{
        for (Map.Entry<String,Student> kv:students.entrySet()){
            db.put(kv.getKey(),toBytes(kv.getValue()));
        }
    }
}
class Student{
    private String name;
    private Integer age;
    private Integer score;

    public Student() {
    }

    public Student(String name, Integer age, Integer score) {
        this.name = name;
        this.age = age;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", score=" + score +
                '}';
    }
}

class Test{
    public static void main(String[] args) throws IOException {
        String path="C:\\Users\\tjp\\Desktop\\test\\";
//        Student s1=new Student("Tom",18,29);
//        Student s2=new Student("Tasom",128,229);
//        Student s3=new Student("am",218,129);
//        Student s4=new Student("isoam",118,429);
//        Student s5=new Student("中十大",8,249);
//        Map<String,Student> map=new HashMap<>();
//        map.put("tom",s1);
//        map.put("2",s2);
//        map.put("3",s3);
//        map.put("4",s4);
//        map.put("5",s5);
        BasicDB basicDB=new BasicDB(path,"student");
//        System.out.println(basicDB.db);
//        BasicDB.saveStudents(basicDB,map);
//        System.out.println("save successfully!");
//        basicDB.flush();
//        basicDB.close();
        byte[] bytes = basicDB.get("5");
//        DataInputStream dataInputStream=new DataInputStream(new ByteArrayInputStream(bytes));
//        System.out.println(dataInputStream.readUTF());
//        System.out.println(dataInputStream.readInt());
//        System.out.println(dataInputStream.readInt());
//        System.out.println(basicDB.db.read());
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        byteArrayOutputStream.write(bytes);
        System.out.println(byteArrayOutputStream.toString());
        basicDB.close();

    }
}