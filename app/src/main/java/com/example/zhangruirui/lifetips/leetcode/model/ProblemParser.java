package com.example.zhangruirui.lifetips.leetcode.model;

import android.util.Log;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangruirui
 * email：1138517609@qq.com
 * GitHub：https://github.com/selfconzrr
 * Blog：http://blog.csdn.net/u011489043
 * Date：12/01/18
 * <p>
 * 剑指 Offer 网页 Html 文档的解析
 * 获取题目名称、题目考察点、题目通过率、题目热度等参数
 */
public class ProblemParser {

  public static List<Problem> parseProblems(Document document) {

    List<Problem> problems = new ArrayList<>();
    Elements elements = document.getElementsByTag("tr"); // 一个 element 对象对应 tr，可以代表一行数据
//    Log.d("zhangrr", "parse Problems size = " + elements.size());
    int problem_id = 1;// 给题目编号
    elements.remove(0); // 第一行为标题栏，不属于有用信息
    for (Element element : elements) {
      String knowledge_point = element.child(0).text();
      String hot_index = element.child(2).text();
      String acceptance = element.child(3).text();

      Elements titleElement = element.child(1).getElementsByTag("a");
      String title = titleElement.get(0).text();
      String url = titleElement.get(0).attr("abs:href"); // 题目的详情页链接

      Problem problem = new Problem(problem_id++, title, acceptance, hot_index, url,
          knowledge_point);
      problems.add(problem);
    }

    return problems;
  }

  public static String parseDetailProblem(Document document) {
    Elements elements = document.getElementsByClass("subject-describe"); // 问题的详细描述
    Log.e("zhangrr", "parseDetailProblem() called with: elements = [" + elements.size() + "]");
    if (elements.size() != 0) {
      Element element = elements.get(0);
      Log.e("zhangrr", "parseDetailProblem() called with: element.html = " + element.html());
      return element.html();
    }

    return null;
  }
}
