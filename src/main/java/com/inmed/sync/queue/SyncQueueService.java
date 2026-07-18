package com.inmed.sync.queue;

import com.inmed.sync.entity.SyncLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@RequiredArgsConstructor
public class SyncQueueService {

    private final BlockingQueue<SyncLog> queue =
            new LinkedBlockingQueue<>();

    public void enqueue(SyncLog log){

        queue.offer(log);

    }

    public SyncLog take()
            throws InterruptedException{

        return queue.take();

    }

}