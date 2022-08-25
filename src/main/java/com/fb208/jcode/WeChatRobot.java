package com.fb208.jcode;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;

public class WeChatRobot {
    public static void main(String[] args) throws AWTException {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tText = null;
        Toolkit tolkit = Toolkit.getDefaultToolkit();
        String[] lists = {"自动发送1,自动发送12", "自动发送13,昨天,今天,明天."};
        Robot robot = new Robot();
        robot.delay(10000);//延迟十秒，主要是为了预留出打开窗口的时间，括号内的单位为毫秒
        for (int i = 0; i < 5; i++) {//循环五次，当然，如果爱得深，你死循环也没问题设置为100
            tText = new StringSelection(lists[i]); //自己定义就需要把这行注释，下行取消注释
//            tText = new StringSelection("爱你每一天");//如果爱得深，把这行取消注释，把内容更换掉你自己想说的
            clip.setContents(tText, null);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            //robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.delay(3000);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.delay(10000);
        }
    }
}
