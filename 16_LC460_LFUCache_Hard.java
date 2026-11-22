class LFUCache {
    private static class CacheNode {
        final int key;
        int value;
        int frequency;

        CacheNode(int key, int value) {
            this.key = key;
            this.value = value;
            this.frequency = 1;
        }
    }

    private final int capacity;
    private final Map<Integer, CacheNode> nodeMap;
    private final Map<Integer, LinkedHashSet<Integer>> freqMap;
    private int minFrequency;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.nodeMap = new HashMap<>();
        this.freqMap = new HashMap<>();
        this.minFrequency = 0;
    }

    public int get(int key) {
        if (!nodeMap.containsKey(key)) return -1;
        CacheNode node = nodeMap.get(key);
        updateFrequency(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity <= 0) return;

        if (nodeMap.containsKey(key)) {
            CacheNode node = nodeMap.get(key);
            node.value = value;
            updateFrequency(node);
            return;
        }

        if (nodeMap.size() == capacity) {
            LinkedHashSet<Integer> minFreqKeys = freqMap.get(minFrequency);
            int evictKey = minFreqKeys.iterator().next();
            minFreqKeys.remove(evictKey);
            nodeMap.remove(evictKey);
        }

        CacheNode newNode = new CacheNode(key, value);
        nodeMap.put(key, newNode);
        freqMap.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
        minFrequency = 1;
    }

    private void updateFrequency(CacheNode node) {
        int oldFreq = node.frequency;
        LinkedHashSet<Integer> oldSet = freqMap.get(oldFreq);
        oldSet.remove(node.key);

        if (oldFreq == minFrequency && oldSet.isEmpty()) {
            minFrequency++;
        }

        node.frequency++;
        freqMap.computeIfAbsent(node.frequency, k -> new LinkedHashSet<>()).add(node.key);
    }
}
/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
