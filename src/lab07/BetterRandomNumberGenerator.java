package lab07;

public class BetterRandomNumberGenerator implements RandomNumberGenerator{
    private long seed;
    private long const1;
    private long const2;
    private long next;

    public int nextInt(int max) {
        seed += 114514;
        return (int)(seed % max);
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public void setConstants(long const1, long const2) {
        this.const1 = const1;
        this.const2 = const2;
    }
}
