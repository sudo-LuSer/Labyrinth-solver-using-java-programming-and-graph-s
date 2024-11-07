import java.util.*;

public class Main {
    static boolean[][] Visited = new boolean[1000][1000];
    static int[][] mp = new int[1000][1000];
    static int[][] Distance = new int[1000][1000];
    static Pair[][] PreviousParent = new Pair[1000][1000];

    static class Pair {
        int xx, yy;

        Pair(int xx, int yy) {
            this.xx = xx;
            this.yy = yy;
        }
    }

    static class C {
        int xx, yy;

        C(int xx, int yy) {
            this.xx = xx;
            this.yy = yy;
        }
    }

    static boolean cmp(C c1, C c2) {
        return c1.xx == c2.xx && c1.yy == c2.yy;
    }

    static boolean checkValidmove(int n, int m, int i, int j) {
        return i >= 0 && i < n && j >= 0 && j < m && !Visited[i][j] && mp[i][j] == 1;
    }

    static void initialize() {
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                Visited[i][j] = false;
                mp[i][j] = 0;
                Distance[i][j] = Integer.MAX_VALUE;
            }
        }
    }

    static void bfs(int xEntry, int yEntry, int n, int m, int xExit, int yExit) {
        Distance[xEntry][yEntry] = 0;
        Deque<C> Q = new ArrayDeque<>();
        C init = new C(xEntry, yEntry);
        C fin = new C(xExit, yExit);
        C sommet = null;
        Q.addLast(init);
        Visited[xEntry][yEntry] = true;

        while (!Q.isEmpty()) {
            C t = Q.pollFirst();

            if (checkValidmove(n, m, t.xx + 1, t.yy) && Distance[t.xx + 1][t.yy] == Integer.MAX_VALUE) {
                Distance[t.xx + 1][t.yy] = Distance[t.xx][t.yy] + 1;
                Visited[t.xx + 1][t.yy] = true;
                sommet = new C(t.xx + 1, t.yy);
                Q.addLast(sommet);
                PreviousParent[sommet.xx][sommet.yy] = new Pair(t.xx, t.yy);
                if (cmp(sommet, fin)) {
                    break;
                }
            }
            if (checkValidmove(n, m, t.xx - 1, t.yy) && Distance[t.xx - 1][t.yy] == Integer.MAX_VALUE) {
                Distance[t.xx - 1][t.yy] = Distance[t.xx][t.yy] + 1;
                Visited[t.xx - 1][t.yy] = true;
                sommet = new C(t.xx - 1, t.yy);
                Q.addLast(sommet);
                PreviousParent[sommet.xx][sommet.yy] = new Pair(t.xx, t.yy);
                if (cmp(sommet, fin)) {
                    break;
                }
            }
            if (checkValidmove(n, m, t.xx, t.yy + 1) && Distance[t.xx][t.yy + 1] == Integer.MAX_VALUE) {
                Distance[t.xx][t.yy + 1] = Distance[t.xx][t.yy] + 1;
                Visited[t.xx][t.yy + 1] = true;
                sommet = new C(t.xx, t.yy + 1);
                Q.addLast(sommet);
                PreviousParent[sommet.xx][sommet.yy] = new Pair(t.xx, t.yy);
                if (cmp(sommet, fin)) {
                    break;
                }
            }
            if (checkValidmove(n, m, t.xx, t.yy - 1) && Distance[t.xx][t.yy - 1] == Integer.MAX_VALUE) {
                Distance[t.xx][t.yy - 1] = Distance[t.xx][t.yy] + 1;
                Visited[t.xx][t.yy - 1] = true;
                sommet = new C(t.xx, t.yy - 1);
                Q.addLast(sommet);
                PreviousParent[sommet.xx][sommet.yy] = new Pair(t.xx, t.yy);
                if (cmp(sommet, fin)) {
                    break;
                }
            }
        }

        if (cmp(sommet, fin)) {
            System.out.println("YES");
            List<Pair> chemin = new ArrayList<>();
            while (!cmp(sommet, init)) {
                chemin.add(new Pair(sommet.xx, sommet.yy));
                C temp = sommet;
                sommet = new C(PreviousParent[temp.xx][temp.yy].xx, PreviousParent[temp.xx][temp.yy].yy);
            }

            System.out.println(chemin.size());
            Collections.reverse(chemin);
            int PosAct_x = init.xx, PosAct_y = init.yy;
            for (Pair p : chemin) {
                int moveX = p.xx - PosAct_x;
                int moveY = p.yy - PosAct_y;
                if (moveX == 0 && moveY == -1) {
                    System.out.print('L');
                } else if (moveX == 0 && moveY == 1) {
                    System.out.print('R');
                } else if (moveX == -1 && moveY == 0) {
                    System.out.print('U');
                } else {
                    System.out.print('D');
                }
                PosAct_x = p.xx;
                PosAct_y = p.yy;
            }
        } else {
            System.out.println("NO");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        initialize();
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int x_a = 0, y_a = 0, x_b = 0, y_b = 0;
        for (int i = 0; i < n; i++) {
            String s = scanner.next();
            for (int j = 0; j < m; j++) {
                if (s.charAt(j) == '.' || s.charAt(j) == 'B' || s.charAt(j) == 'A') {
                    if (s.charAt(j) == 'A') {
                        x_a = i;
                        y_a = j;
                    } else if (s.charAt(j) == 'B') {
                        x_b = i;
                        y_b = j;
                    }
                    mp[i][j] = 1;
                }
            }
        }
        bfs(x_a, y_a, n, m, x_b, y_b);
        scanner.close();
    }
}