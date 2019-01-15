package com.paas.services;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.paas.PaaSApplicationException;
import com.paas.repository.dao.DataReader;
import com.paas.utils.StringToPath;;

@Service
@Scope("prototype")
public class FileSystemWatchService {

	private WatchService watcher;

	private ExecutorService executor;

	@SuppressWarnings("rawtypes")
	private DataReader reader;

	public void setFilteToWatch(String filteToWatch) {
		this.filteToWatch = filteToWatch;
	}

	private String filteToWatch;

	@PostConstruct
	private void initWatchService() throws IOException {
		watcher = FileSystems.getDefault().newWatchService();
		executor = Executors.newSingleThreadExecutor();
	}

	@PreDestroy
	private void cleanup() {
		try {
			watcher.close();
		} catch (IOException ie) {

		}
		executor.shutdown();
	}

	public void startWatchService() throws PaaSApplicationException {
		try {
			registerDirectory();
			processEvents();
		} catch (PaaSApplicationException e) {
			throw new PaaSApplicationException(FileSystemWatchService.class,
					"Error registering directory with watch service");
		}
	}

	private void registerDirectory() throws PaaSApplicationException {
		Path path = StringToPath.getPath(filteToWatch);
		Path dir = path.getParent();
		try {
			dir.register(watcher, ENTRY_MODIFY);
		} catch (IOException e) {
			throw new PaaSApplicationException(FileSystemWatchService.class,
					"Error registering directory with watch service");
		}
	}

	@SuppressWarnings("unchecked")
	private void processEvents() throws PaaSApplicationException {
		executor.submit(() -> {

			try {

				Path path = StringToPath.getPath(filteToWatch);
				Path dir = path.getParent();

				while (true) {
					final WatchKey key;
					try {
						key = watcher.take(); // wait for a key to be available
					} catch (InterruptedException ex) {
						return;
					}

					key.pollEvents().stream().filter(e -> (e.kind() != OVERFLOW))
							.map(e -> ((WatchEvent<Path>) e).context()).forEach(p -> {
								final Path absPath = dir.resolve(p);
								if (absPath.equals(path)) {
									notifySubscriber();
								}
							});
					boolean valid = key.reset(); // IMPORTANT: The key must be reset after processed
					if (!valid) {
						break;
					}
				}
			} catch (PaaSApplicationException e1) {
				// TODO Auto-generated catch block
			}
		});
	}

	public void setSubsciber(@SuppressWarnings("rawtypes") DataReader dataReader) {
		reader = dataReader;
	}

	private void notifySubscriber() {
		reader.setUpdateNeeded();
	}
}
