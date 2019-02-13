package com.springmvc.lxy.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.*;
import java.net.URI;

/**
 * 描述: ${description}
 * <p>
 *
 * @author: harry
 * @date: 2018-11-18
 **/
public class HadoopExec {
    public static void main(String[] args) throws IOException {
//        print();
//
//        makeDirectory();

        uploadData();

//        downloadData();
    }

    /**
     * 读取文件
     * 
     * @throws IOException
     */
    public static void print() throws IOException {
        String file = "hdfs://localhost:9000/middle/weibo/weibo.txt";
        FileSystem fs= FileSystem.get(URI.create(file), new Configuration());
        FSDataInputStream is= fs.open(new Path(file));
        byte[] buff=new byte[1024];
        int length=0;
        // 打印每一行文字内容
        while((length=is.read(buff))!=-1){
            System.out.println(new String(buff,0,length));
        }
    }

    /**
     * 创建目录
     * 
     * @throws IOException
     */
    public static void makeDirectory() throws IOException {
        FileSystem fileSystem = FileSystem.get(URI.create("hdfs://localhost:9000/"), new Configuration());
        fileSystem.mkdirs(new Path("hdfs://localhost:9000/helloworld"));
    }

    /**
     * 上传文件
     * 
     * @throws IOException
     */
    public static void uploadData() throws IOException {
        FileSystem fileSystem = FileSystem.get(URI.create("hdfs://localhost:9000/"), new Configuration());
        // 上传文件的路径和名称
        final FSDataOutputStream out = fileSystem.create(new Path("hdfs://localhost:9000/helloworld/test1"));
        FileInputStream in = new FileInputStream("/Users/harry/Desktop/test1"); //上传的文件
        IOUtils.copyBytes(in, out, 1024, true);
    }

    /**
     * 下载文件到本地
     * 
     * @throws IOException
     */
    public static void downloadData() throws IOException {
        FileSystem fileSystem = FileSystem.get(URI.create("hdfs://localhost:9000/"), new Configuration());
        //要下载的文件
        final FSDataInputStream in = fileSystem.open(new Path("hdfs://localhost:9000/helloworld/test1"));
        OutputStream os=new FileOutputStream(new File("/Users/harry/Desktop/word2.txt"));
        IOUtils.copyBytes(in, os, 1024, true); //只是打印可以把os换成System.out
    }

    /**
     * 删除文件
     *
     * @throws IOException
     */
    public static void deleteFile() throws IOException {
        FileSystem fileSystem = FileSystem.get(URI.create("hdfs://192.168.0.229:8020/"), new Configuration());
        // 删除文件-true/false（文件夹-true）
        fileSystem.delete(new Path("hdfs://192.168.0.229:8020/helloworld"), true);
    }
}
