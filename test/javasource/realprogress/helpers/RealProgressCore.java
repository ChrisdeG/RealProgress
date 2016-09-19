package realprogress.helpers;

import java.util.Date;
import realprogress.helpers.ProgressStatus;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.systemwideinterfaces.core.ISession;



/**
 * A singleton for sharing progress data between microflows and gui.
 *
 * @author Chris de Gelder - 2016
 * 
 */
public class RealProgressCore  {

	/* not used keys are removed are X minutes */
	private static final int LIVETIME_MINUTES = 15;
	/** The instance. */
	private static RealProgressCore instance = null;
	/* storage of the progress */
	private static Map<String, ProgressStatus> progressMap = new ConcurrentHashMap<String, ProgressStatus>();
	// background process for cleanup
	private static Runnable cleanupProcess = null;

	/**
	 * Gets the single instance of LuceneFactory.
	 *
	 * @return single instance of LuceneFactory
	 */
	public static RealProgressCore getInstance() {
		if(instance == null) {
			instance = new RealProgressCore();
			cleanupProcess = new Runnable() {
				public void run() {
					// cleanup old non used keys
					Long ago = new Date().getTime() - (LIVETIME_MINUTES * 60 * 1000); 
					for (Map.Entry<String, ProgressStatus> entry : progressMap.entrySet())
					{
						if (entry.getValue().getLastRetrieved().getTime() < ago ) {
							Core.getLogger("RealProgress").trace("cleanup key" + entry.getKey());
							progressMap.remove(entry.getKey());
						}
					}
				}
			};
			ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
			service.scheduleAtFixedRate(cleanupProcess, 0, 15, TimeUnit.MINUTES);		      
		}
		return instance;
	}
	/*
	 * sets the progress based on a mendix object and a session
	 */

	public Long getProgress(IMendixObject mendixObject, ISession session){
		String mendixId = getId(mendixObject, session);
		if (progressMap.containsKey(mendixId)) {
			ProgressStatus progressStatus = progressMap.get(mendixId);
			return Long.valueOf(Math.round((100.0 * progressStatus.getCurrent() / progressStatus.getMax())));
		} else {
			return 0L;
		}
	}
	
	public String getProgressMessage(IMendixObject mendixObject, ISession session){
		String mendixId = getId(mendixObject, session);
		if (progressMap.containsKey(mendixId)) {
			ProgressStatus progressStatus = progressMap.get(mendixId);
			return progressStatus.getMessage();
		} else {
			return null;
		}
	}	
	/*
	 * Set the progress 
	 */

	public boolean setProgress(IMendixObject mendixObject, ISession session, int progress){
		String mendixId = getId(mendixObject, session);		
		ProgressStatus progressStatus = null;
		progressStatus = progressMap.get(mendixId);
		if (progressStatus == null) {
			progressStatus = new ProgressStatus(100, mendixId);
		}
		progressStatus.setCurrent(progress);
		progressMap.put(mendixId, progressStatus);
		return true;
	}
	/*
	 * sets a message
	 */
	public boolean setMessage(IMendixObject mendixObject, ISession session, String message){
		String mendixId = getId(mendixObject, session);		
		ProgressStatus progressStatus = null;
		progressStatus = progressMap.get(mendixId);
		if (progressStatus == null) {
			progressStatus = new ProgressStatus(100, mendixId);
		}
		progressStatus.setMessage(message);
		progressMap.put(mendixId, progressStatus);
		return true;
	}	
	/*
	 * create a unique key to identify the process.
	 */

	public String getId(IMendixObject mendixObject, ISession session) {
		if (session != null) {
			return Long.toString(mendixObject.getId().toLong()) + "-" + session.getId();
		} 
		return Long.toString(mendixObject.getId().toLong());
	}

}
