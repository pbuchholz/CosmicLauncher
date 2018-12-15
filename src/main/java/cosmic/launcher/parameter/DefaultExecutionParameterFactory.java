package cosmic.launcher.parameter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

/**
 * Implementation of {@link ExecutionParameterFactory} which uses {@link Jsonb}.
 * 
 * @author Philipp Buchholz
 */
public class DefaultExecutionParameterFactory implements ExecutionParameterFactory {

	private Jsonb jsonb = JsonbBuilder.create();

	private List<ExecutionParameter> cachedExecutionParameters = null;

	@Override
	public List<ExecutionParameter> findExecutionParameterFor(ToExecutableMapping toExecutableMapping) {
		if (null == this.cachedExecutionParameters) {
			this.cacheExecutionParameters();
		}

		return this.cachedExecutionParameters.stream() //
				.filter((parameter) -> parameter.getToExecutableMapping().equals(toExecutableMapping)) //
				.collect(Collectors.toList());
	}

	/**
	 * Fills the cache of {@link ExecutionParameter}s.
	 */
	@SuppressWarnings("serial")
	private void cacheExecutionParameters() {
		try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("execution-parameters.json")) {
			this.cachedExecutionParameters = this.jsonb.fromJson(inputStream, new ArrayList<ExecutionParameter>() {
			}.getClass().getGenericSuperclass());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
