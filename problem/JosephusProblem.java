import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class JosephusProblem {
  /**
   * 约瑟夫环问题 N 个人围成一圈报数（0~N-1），报到M的人会被杀死，直到最后剩下一个人，最后存活者的编号是多少？
   * 
   * 类似练习题 leetcode 55, 300, 560
   * 
   * 
   * @param N
   * @param M
   * @return -1 输入的N，M超出范围；最后存活者的编号
   */
  public static int lastLive(int N, int M) {
    if (N < 0 || M < 0) {
      return -1;
    }
    Deque<Integer> q = new ArrayDeque<>();
    for (int i = 0; i < N; ++i) {
      q.offer(i);
    }

    while (q.size() > 1) {
      M %= q.size() + 1;
      for (int i = 0; i < M - 1; ++i) {
        q.offer(q.poll());
      }
      System.out.println(q.poll());
    }
    return q.poll();
  }

  public static int lastLive2(int N, int M) {
    int s = 0;
    for (int i = 2; i <= N; ++i) {
      System.out.println(s);
      s = (s + M) % i;
    }
    return s;
  }

  public static void main(String[] args) {
    System.out.println(lastLive2(7, 2));
  }
}