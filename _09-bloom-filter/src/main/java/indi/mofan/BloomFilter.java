package indi.mofan;

/**
 * @author mofan
 * @date 2022/12/3 17:08
 */
public class BloomFilter<T> {

    /**
     * 二进制向量的长度（一共有多少个二进制位）
     */
    private final int bitSize;

    /**
     * <p>长整数数组，存储二进制向量</p>
     * <p>long 占 8 字节，1 字节 8 bit，因此一个 long 可以表示 64 位</p>
     */
    private final long[] bits;

    /**
     * 哈希函数的个数
     */
    private final int hashSize;

    /**
     * 布隆过滤器构造方法
     *
     * @param n 数据规模
     * @param p 允许的误判率，范围 (0, 1)
     */
    public BloomFilter(int n, double p) {
        if (n <= 0 || p <= 0 || p >= 1) {
            throw new IllegalArgumentException("wrong n or p");
        }
        double ln2 = Math.log(2);
        // 二进制向量的长度
        bitSize = (int) (-(n * Math.log(p)) / (ln2 * ln2));
        // 哈希函数的个数
        hashSize = (int) (bitSize * ln2 / n);
        // bits 数组的长度，向上取整
        bits = new long[(bitSize + Long.SIZE - 1) / Long.SIZE];
    }

    /**
     * 添加元素
     *
     * @param value 被添加的元素
     * @return value 的添加是否造成了二进制位的变化
     */
    public boolean put(T value) {
        nullCheck(value);

        int hash1 = value.hashCode();
        int hash2 = hash1 >>> 16;

        boolean result = false;
        for (int i = 1; i <= hashSize; i++) {
            int combinedHash = hash1 + (i * hash2);
            if (combinedHash < 0) {
                combinedHash = ~combinedHash;
            }
            // 生成索引
            int index = combinedHash % bitSize;
            // 将 index 位置的二进制位设置为 1
            if (this.set(index)) result = true;
        }
        return result;
    }

    /**
     * 判断某个元素是否存在
     *
     * @param value 目标元素
     * @return 不存在 --> false，存在 --> true
     */
    public boolean contains(T value) {
        nullCheck(value);
        // 利用value生成2个整数
        int hash1 = value.hashCode();
        int hash2 = hash1 >>> 16;

        for (int i = 1; i <= hashSize; i++) {
            int combinedHash = hash1 + (i * hash2);
            if (combinedHash < 0) {
                combinedHash = ~combinedHash;
            }
            // 生成一个二进制的索引
            int index = combinedHash % bitSize;
            // 查询第index位置的二进制是否为0
            if (!this.get(index)) return false;
        }
        return true;
    }

    /**
     * 设置 index 位置的二进制位为 1
     *
     * @param index 目标索引
     * @return index 位置的二进制位是否从 0 变成了 1
     */
    private boolean set(int index) {
        // 计算 index 位于 long 数组中的哪一个 long 中
        long value = bits[index / Long.SIZE];
        // 找到 index 在 long 中的哪个二进制位
        long bitValue = 1L << (index % Long.SIZE);
        // 将二进制位设置为 1 后再重新赋值
        bits[index / Long.SIZE] = value | bitValue;
        // 判断 index 位置的二进制位是否从 0 变成了 1
        return (value & bitValue) == 0;
    }

    /**
     * 获取 index 位置二进制位的值
     *
     * @param index 目标索引
     * @return true --> 1，false --> 0
     */
    private boolean get(int index) {
        // 计算 index 位于 long 数组中的哪一个 long 中
        long value = bits[index / Long.SIZE];
        return (value & (1L << (index % Long.SIZE))) != 0;
    }

    private void nullCheck(T value) {
        if (value == null) {
            throw new IllegalArgumentException("value must not be null.");
        }
    }
}
