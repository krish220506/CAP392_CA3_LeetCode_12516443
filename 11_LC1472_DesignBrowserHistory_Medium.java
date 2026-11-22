class BrowserHistory {
    private ArrayList<String> history;
    private int currentIndex;

    public BrowserHistory(String homepage) {
        this.history = new ArrayList<>();
        this.history.add(homepage);
        this.currentIndex = 0;
    }

    public void visit(String url) {
        while (this.history.size() - 1 > this.currentIndex) {
            this.history.remove(this.history.size() - 1);
        }
        this.history.add(url);
        this.currentIndex++;
    }

    public String back(int steps) {
        this.currentIndex = Math.max(0, this.currentIndex - steps);
        return this.history.get(this.currentIndex);
    }

    public String forward(int steps) {
        this.currentIndex = Math.min(this.history.size() - 1, this.currentIndex + steps);
        return this.history.get(this.currentIndex);
    }
}

/**
 * Your BrowserHistory object will be instantiated and called as such:
 * BrowserHistory obj = new BrowserHistory(homepage);
 * obj.visit(url);
 * String param_2 = obj.back(steps);
 * String param_3 = obj.forward(steps);
 */
