package jp.lambdamagic.web.http.service;

import jp.lambdamagic.event.log.EventLog;
import jp.lambdamagic.resource.ResourceLoader;

public interface ServiceContext extends EventLog, ResourceLoader {
}
