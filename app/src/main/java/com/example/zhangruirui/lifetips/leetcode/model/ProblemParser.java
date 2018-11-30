package com.example.zhangruirui.lifetips.leetcode.model;

import android.util.Log;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ProblemParser {
  public static List<Problem> parseProblems(Document document) {

    List<Problem> problems = new ArrayList<>();
    Elements elements = document.getElementsByTag("tr"); // 一个 element 对象对应 tr，可以代表一行数据
    Log.d("zhangrr", "parse Problems size = " + elements.size());
    int problem_id = 1;
    elements.remove(0);
    for (Element element : elements) {
      String knowledge_point = element.child(0).text();
      String acceptance = element.child(3).text();
      String hot = element.child(2).text();
      Log.e("zhangrr", "parseProblems() called with: knowledge_point = " + knowledge_point + " " +
          "acceptance = " + acceptance + " hot = " + hot);

      Elements titleElement = element.child(1).getElementsByTag("a");
      String title = titleElement.get(0).text();
      String url = titleElement.get(0).attr("abs:href");
      Log.e("zhangrr", "parseProblems() called with: title = " + title + " url = " + url);

      Problem problem = new Problem(problem_id++, title, acceptance, hot, url, knowledge_point);
      problems.add(problem);
    }

    return problems;
  }

  public static String parseDetailProblem(Document document) {
    Elements elements = document.getElementsByClass("question-content");
    if (elements.size() == 1) {
      Element element = elements.get(0);
      Log.e("zhangrr", "parseDetailProblem() called with: element.html = " + element.html());
      return element.html();
    }

    return null;
  }
}
