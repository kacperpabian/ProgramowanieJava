public class TestBacteria {
    private int genotype;
    private int alpha;
    private int beta;
    private int gamma;

    public int getGenotype() {
        return genotype;
    }

    public void setGenotype(int genotype) {
        this.genotype = genotype;
        convert();
    }

    public int getAlpha() {
        return alpha;
    }

    public int getBeta() {
        return beta;
    }


    public int getGamma() {
        return gamma;
    }

    private void convert(){
        String temp;
        temp=String.valueOf(Integer.toString(genotype).charAt(0))+String.valueOf(Integer.toString(genotype).charAt(5));
        alpha=Integer.parseInt(temp);

        temp=String.valueOf(Integer.toString(genotype).charAt(1))+String.valueOf(Integer.toString(genotype).charAt(4));
        beta=Integer.parseInt(temp);

        temp=String.valueOf(Integer.toString(genotype).charAt(2))+String.valueOf(Integer.toString(genotype).charAt(3));
        gamma=Integer.parseInt(temp);
    }
}
