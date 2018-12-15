package cosmic.launcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cosmic.launcher.decorator.Log;
import cosmic.launcher.execution.Executable;
import cosmic.launcher.execution.ExecutionContext;
import cosmic.launcher.parameter.ExecutionParameter;

/**
 * Implementation of {@link Executable} which copies a source directory to a
 * destination directory.
 * 
 * @author Philipp Buchholz
 */
public class CopyDirectoryExecuteable implements Executable {

	private static final Logger LOGGER = LoggerFactory.getLogger(CopyDirectoryExecuteable.class);

	private static final String EXECUTABLE_NAME = "CopyDirectory";
	private static final String SRC_DIR = "src-dir";
	private static final String DEST_DIR = "dest-dir";

	@Log
	@Override
	public void execute(ExecutionContext executionContext) {
		try {
			ExecutionParameter srcDirParam = executionContext.executionParameterByName(SRC_DIR);
			ExecutionParameter destDirParam = executionContext.executionParameterByName(DEST_DIR);

			assert (null != srcDirParam);
			assert (null != destDirParam);

			Path sourcePath = Paths.get(srcDirParam.getValue());
			Path destinationPath = Paths.get(destDirParam.getValue());

			this.copyRecursively(sourcePath, destinationPath);
		} catch (Exception e) {
			LOGGER.error("CopyDirectoryExecutable crashed with {} and will not be restarted.", e);
			throw e;
		}
	}

	@Log
	private void copyRecursively(Path sourcePath, Path destinationPath) {
		Stream.of(sourcePath.toFile().listFiles()).forEach((file) -> {
			try {
				Path subPath = destinationPath.resolve(file.getName());

				try {
					if (file.isDirectory()) {
						subPath.toFile().mkdir();
						this.copyRecursively(file.toPath(), subPath);
					} else {
						LOGGER.info("Copy file {} to {}", file, subPath);
						Files.copy(file.toPath(), subPath, StandardCopyOption.REPLACE_EXISTING);
					}
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			} catch (Exception e) {
				throw e;
			}
		});
	}

	@Override
	public String name() {
		return EXECUTABLE_NAME;
	}

}
