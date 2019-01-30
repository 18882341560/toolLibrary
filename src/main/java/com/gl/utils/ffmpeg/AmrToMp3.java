package com.gl.utils.ffmpeg;

import it.sauronsoftware.jave.*;

import java.io.*;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AmrToMp3 {


    //windows 用这个
    public static void changeTemp(String sourcePath) throws Exception {
        File source = new File(sourcePath);   //源文件
        String substring = sourcePath.substring(0, sourcePath.lastIndexOf("."));
        String mp3FilePath = substring + ".mp3";
        File target = new File(mp3FilePath);   //目标文件
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libmp3lame");
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp3");
        attrs.setAudioAttributes(audio);
        Encoder encoder = new Encoder();
        encoder.encode(source, target, attrs);
    }

    public static void changeAmrToMp3(String sourcePath, String targetPath) {
        File source = new File(sourcePath);
        File target = new File(targetPath);
        AudioAttributes audio = new AudioAttributes();
        Encoder encoder = new Encoder();

        audio.setCodec("libmp3lame");
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp3");
        audio.setBitRate(new Integer(128000));
        audio.setChannels(new Integer(2));
        audio.setSamplingRate(new Integer(44100));
        attrs.setAudioAttributes(audio);

        try {
            encoder.encode(source, target, attrs);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InputFormatException e) {
            e.printStackTrace();
        } catch (EncoderException e) {
            e.printStackTrace();
        }
    }


    /**
     * 判断系统是Windows还是linux并且拼接ffmpegPath
     * @return
     */
    private static String getLinuxOrWindowsFfmpegPath() {
        String ffmpegPath = "";
        String osName = System.getProperties().getProperty("os.name");
        if (osName.toLowerCase().indexOf("linux") >= 0) {
            ffmpegPath = "";
        } else {
            URL url = Thread.currentThread().getContextClassLoader().getResource("ffmpeg/windows/");
//            URL url = AmrToMp3.class.getClassLoader().getResource("ffmpeg/ffmpeg/");
            if (url != null) {
                ffmpegPath = url.getFile();
            }
        }
        System.out.println("ffmpegPath:"+ffmpegPath);
        return ffmpegPath;
    }


    /**
     * linux 用这个
     * 将amr文件输入流转为mp3格式
     * @param path  文件名（包含后缀）
     * @return
     */
    public static String amrToMP3(String path) throws Exception{
        String ffmpegPath = getLinuxOrWindowsFfmpegPath();
        Runtime runtime = Runtime.getRuntime();
        String substring = path.substring(0, path.lastIndexOf("."));
        String mp3FilePath = substring + ".mp3";
        Process p = null;
        try {
//            String filePath = copyFile(inputStream, fileName);
            //执行ffmpeg文件，将amr格式转为mp3
            //filePath ----> amr文件在临时文件夹中的地址
            //mp3FilePath  ----> 转换后的mp3文件地址
            p = runtime.exec(ffmpegPath + "ffmpeg -i" + " " +path + " " + mp3FilePath);//执行ffmpeg.exe,前面是ffmpeg.exe的地址，中间是需要转换的文件地址，后面是转换后的文件地址。-i是转换方式，意思是可编码解码，mp3编码方式采用的是libmp3lame
            System.out.println("调用命令:"+ffmpegPath + "ffmpeg -i" + " " +path + " " + mp3FilePath);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放进程
            if(p != null){
                p.getOutputStream().close();
                p.getInputStream().close();
                p.getErrorStream().close();
                p.waitFor(); // 等待命令执行完毕
            }
            runtime.freeMemory();
        }
        return mp3FilePath;
    }


    /**
     * 将用户输入的amr音频文件流转为音频文件并存入临时文件夹中
     * @param inputStream  输入流
     * @param fileName  文件姓名
     * @return  amr临时文件存放地址
     * @throws IOException
     */
    private static String copyFile(InputStream inputStream, String fileName) throws IOException {

//        String filePath = FilePathUtils.uploadFilesUrl; //创建临时目录
        String filePath = ""; //创建临时目录
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String outPutFile = filePath + File.separator + fileName;
        OutputStream outputStream = new FileOutputStream(outPutFile);
        int bytesRead;
        byte[] buffer = new byte[8192];
        while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
        return outPutFile;
    }


    public static void main(String[] args) throws Exception {
//        changeTemp();
//        amrToMP3(null,null);
//        System.out.println(getLinuxOrWindowsFfmpegPath());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime1 = LocalDateTime.parse("2019-01-22 09:25:10",df);
        LocalDateTime dateTime2 = LocalDateTime.parse("2019-01-22 16:30:20",df);


        // 比较第一个提醒时间是否在当前时间15分钟内
        Duration duration = Duration.between(dateTime1,dateTime2);
        System.out.println(duration.getSeconds());
    }

}
