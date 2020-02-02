package com.wz.libnetwork.cache;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * @author wangzhen
 * @date 2020/02/02
 */
@Entity(tableName = "cache")
public class Cache implements Serializable {
    @NonNull
    @PrimaryKey
    public String key;
    public byte[] data;
}
