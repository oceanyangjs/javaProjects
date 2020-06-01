package com.chan.szys.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SensitiveWordFilter {
    public static Map sensitiveWordMap;
    public static int minMatchTYpe = 1;      //最小匹配规则
    public static int maxMatchType = 2;      //最大匹配规则
    private static String replaceString = null;

    /**
     * 例如：敏感词中含有中国人、中国
     * 最小匹配规则minMatchTYpe为1时,会匹配出**人，为2时，会匹配出***
     */
    public static void main(String[] args) throws Exception {
        SensitiveWordFilter filter = new SensitiveWordFilter();
        System.out.println("敏感词的数量：" + filter.sensitiveWordMap.size());
        String string = "dfa是面向三级装配的设计(Design for assembly)的英文简称，是指在产品设计阶段设计产品使得产品具有良好" + "的可装配性，确保装配工序简单、装配效率高、装配质量高、装配不良率低和装配成本低。面向装配的设计通过一系" + "列有利于装配的设计指南例如简化产品设计、减少零件数量等，女女并同装配工程师一起合作，被逼简化产品结构，近親使其便于" +
                "装配，为提高产品质量、缩短产品开发周期和降低产品成本奠定基础";
        // ------获取敏感词---------
        Set<String> set = filter.getSensitiveWord(string, 1);
        System.out.println("含敏感词的个数为：" + set.size() + "。包含：" + set);
        // ------------------------替换敏感字begin----------------------
        Iterator<String> iterator = set.iterator();
        String word = null;
        while (iterator.hasNext()) {
            word = iterator.next();
            /**
             * 得到word中敏感关键词被替换后的字符串，例如：***
             * */
            getReplaceCharsS("*", word.length());
            /**
             * 将原字符串中的敏感关键词替换成带有replaceChar
             * 或全部为replaceChar的关键词
             * */
            string = string.replaceAll(word, replaceString);
        }
        // ------------------------替换敏感字end----------------------
        System.out.println(string);
    }

    /**
     * 构造函数，初始化敏感词库
     */
    public SensitiveWordFilter() {
        sensitiveWordMap = null;
        sensitiveWordMap = new SensitiveWordInit().initKeyWord();
    }

    /**
     * 判断文字是否包含敏感字符
     *
     * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
     */
    public boolean isContaintSensitiveWord(String txt, int matchType) {
        boolean flag = false;
        for (int i = 0; i < txt.length(); i++) {
            int matchFlag = this.CheckSensitiveWord(txt, i, matchType); //判断是否包含敏感字符
            if (matchFlag > 0) {    //大于0存在，返回true
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 获取文字中的敏感词
     *
     * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
     */
    public Set<String> getSensitiveWord(String txt, int matchType) {
        Set<String> sensitiveWordList = new HashSet<String>();

        for (int i = 0; i < txt.length(); i++) {
            int length = CheckSensitiveWord(txt, i, matchType);    //判断是否包含敏感字符
            if (length > 0) {    //存在,加入list中
                sensitiveWordList.add(txt.substring(i, i + length));
                i = i + length - 1;    //减1的原因，是因为for会自增
            }
        }

        return sensitiveWordList;
    }

    /**
     * 替换敏感字字符,默认*
     */
    public String replaceSensitiveWord(String txt, int matchType, String replaceChar) {
        String resultTxt = txt;
        Set<String> set = getSensitiveWord(txt, matchType);     //获取所有的敏感词
        Iterator<String> iterator = set.iterator();
        String word = null;
        String replaceString = null;
        while (iterator.hasNext()) {
            word = iterator.next();
            replaceString = getReplaceChars(replaceChar, word.length());
            resultTxt = resultTxt.replaceAll(word, replaceString);
        }

        return resultTxt;
    }

    /**
     * 获取替换字符串
     */
    private String getReplaceChars(String replaceChar, int length) {
        String resultReplace = replaceChar;
        for (int i = 1; i < length; i++) {
            resultReplace += replaceChar;
        }

        return resultReplace;
    }

    /**
     * 获取替换字符串,无返回值
     */
    private static void getReplaceCharsS(String replaceChar, int length) {
        replaceString = "";
        String resultReplace = replaceChar;
        for (int i = 1; i < length; i++) {
            resultReplace += replaceChar;
        }
        replaceString = resultReplace;
    }

    /**
     * 检查文字中是否包含敏感字符，检查规则如下：<br>
     */
    @SuppressWarnings({"rawtypes"})
    public int CheckSensitiveWord(String txt, int beginIndex, int matchType) {
        boolean flag = false;    //敏感词结束标识位：用于敏感词只有1位的情况
        int matchFlag = 0;     //匹配标识数默认为0
        char word = 0;
        Map nowMap = sensitiveWordMap;
        for (int i = beginIndex; i < txt.length(); i++) {
            word = txt.charAt(i);
            nowMap = (Map) nowMap.get(word);     //获取指定key
            if (nowMap != null) {     //存在，则判断是否为最后一个
                matchFlag++;     //找到相应key，匹配标识+1
                if ("1".equals(nowMap.get("isEnd"))) {       //如果为最后一个匹配规则,结束循环，返回匹配标识数
                    flag = true;       //结束标志位为true
                    if (SensitiveWordFilter.minMatchTYpe == matchType) {    //最小规则，直接返回,最大规则还需继续查找
                        break;
                    }
                }
            } else {     //不存在，直接返回
                break;
            }
        }
        if (matchFlag < 2 || !flag) {        //长度必须大于等于1，为词
            matchFlag = 0;
        }
        return matchFlag;
    }

}
