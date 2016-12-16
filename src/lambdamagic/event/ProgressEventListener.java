package lambdamagic.event;

import java.util.EventListener;

@FunctionalInterface
public interface ProgressEventListener extends EventListener {
	void onProgressChanged(ProgressEvent e);
}
