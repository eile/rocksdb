// Copyright (c) 2014, Facebook, Inc.  All rights reserved.
// This source code is licensed under the BSD-style license found in the
// LICENSE file in the root directory of this source tree. An additional grant
// of patent rights can be found in the PATENTS file in the same directory.

package org.rocksdb.test;

import org.junit.ClassRule;
import org.junit.Test;
import org.rocksdb.*;

import static org.assertj.core.api.Assertions.assertThat;

public class BlockBasedTableConfigTest {

  @ClassRule
  public static final RocksMemoryResource rocksMemoryResource =
      new RocksMemoryResource();

  @Test
  public void noBlockCache() {
    BlockBasedTableConfig blockBasedTableConfig = new BlockBasedTableConfig();
    blockBasedTableConfig.setNoBlockCache(true);
    assertThat(blockBasedTableConfig.noBlockCache()).isTrue();
  }

  @Test
  public void blockCacheSize() {
    BlockBasedTableConfig blockBasedTableConfig = new BlockBasedTableConfig();
    blockBasedTableConfig.setBlockCacheSize(8 * 1024);
    assertThat(blockBasedTableConfig.blockCacheSize()).
        isEqualTo(8 * 1024);
  }

  @Test
  public void blockSizeDeviation() {
    BlockBasedTableConfig blockBasedTableConfig = new BlockBasedTableConfig();
    blockBasedTableConfig.setBlockSizeDeviation(12);
    assertThat(blockBasedTableConfig.blockSizeDeviation()).
        isEqualTo(12);
  }

  @Test
  public void blockRestartInterval() {
    BlockBasedTableConfig blockBasedTableConfig = new BlockBasedTableConfig();
    blockBasedTableConfig.setBlockRestartInterval(15);
    assertThat(blockBasedTableConfig.blockRestartInterval()).
        isEqualTo(15);
  }

  @Test
  public void wholeKeyFiltering() {
    BlockBasedTableConfig blockBasedTableConfig = new BlockBasedTableConfig();
    blockBasedTableConfig.setWholeKeyFiltering(false);
    assertThat(blockBasedTableConfig.wholeKeyFiltering()).
        isFalse();
  }

  @Test
  public void cacheIndexAndFilterBlocks() {
    BlockBasedTableConfig blockBasedTableConfig = new BlockBasedTableConfig();
    blockBasedTableConfig.setCacheIndexAndFilterBlocks(true);
    assertThat(blockBasedTableConfig.cacheIndexAndFilterBlocks()).
        isTrue();

  }

  @Test
  public void hashIndexAllowCollision() {
    BlockBasedTableConfig blockBasedTableConfig = new BlockBasedTableConfig();
    blockBasedTableConfig.setHashIndexAllowCollision(false);
    assertThat(blockBasedTableConfig.hashIndexAllowCollision()).
        isFalse();
  }

  @Test
  public void blockCacheCompressedSize() {
    BlockBasedTableConfig blockBasedTableConfig = new BlockBasedTableConfig();
    blockBasedTableConfig.setBlockCacheCompressedSize(40);
    assertThat(blockBasedTableConfig.blockCacheCompressedSize()).
        isEqualTo(40);
  }

  @Test
  public void checksumType() {
    BlockBasedTableConfig blockBasedTableConfig = new BlockBasedTableConfig();
    blockBasedTableConfig.setChecksumType(ChecksumType.kNoChecksum);
    blockBasedTableConfig.setChecksumType(ChecksumType.kxxHash);
    assertThat(blockBasedTableConfig.checksumType().equals(
        ChecksumType.kxxHash));
  }

  @Test
  public void indexType() {
    BlockBasedTableConfig blockBasedTableConfig = new BlockBasedTableConfig();
    assertThat(IndexType.values().length).isEqualTo(2);
    blockBasedTableConfig.setIndexType(IndexType.kHashSearch);
    assertThat(blockBasedTableConfig.indexType().equals(
        IndexType.kHashSearch));
    assertThat(IndexType.valueOf("kBinarySearch")).isNotNull();
    blockBasedTableConfig.setIndexType(IndexType.valueOf("kBinarySearch"));
    assertThat(blockBasedTableConfig.indexType().equals(
        IndexType.kBinarySearch));
  }

  @Test
  public void blockCacheCompressedNumShardBits() {
    BlockBasedTableConfig blockBasedTableConfig = new BlockBasedTableConfig();
    blockBasedTableConfig.setBlockCacheCompressedNumShardBits(4);
    assertThat(blockBasedTableConfig.blockCacheCompressedNumShardBits()).
        isEqualTo(4);
  }

  @Test
  public void cacheNumShardBits() {
    BlockBasedTableConfig blockBasedTableConfig = new BlockBasedTableConfig();
    blockBasedTableConfig.setCacheNumShardBits(5);
    assertThat(blockBasedTableConfig.cacheNumShardBits()).
        isEqualTo(5);
  }

  @Test
  public void blockSize() {
    BlockBasedTableConfig blockBasedTableConfig = new BlockBasedTableConfig();
    blockBasedTableConfig.setBlockSize(10);
    assertThat(blockBasedTableConfig.blockSize()).isEqualTo(10);
  }


  @Test
  public void blockBasedTableWithFilter() {
    Options options = null;
    try {
      options = new Options();
      options.setTableFormatConfig(
          new BlockBasedTableConfig().setFilter(
              new BloomFilter(10)));
      assertThat(options.tableFactoryName()).
          isEqualTo("BlockBasedTable");
    } finally {
      if (options != null) {
        options.dispose();
      }
    }
  }

  @Test
  public void blockBasedTableWithoutFilter() {
    Options options = null;
    try {
      options = new Options();
      options.setTableFormatConfig(
          new BlockBasedTableConfig().setFilter(null));
      assertThat(options.tableFactoryName()).
          isEqualTo("BlockBasedTable");
    } finally {
      if (options != null) {
        options.dispose();
      }
    }
  }
}
