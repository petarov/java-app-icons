package net.vexelon.appicons.wireframe;

import net.vexelon.appicons.wireframe.entities.IconURL;

public interface NamingStrategyResolver {

    String resolve(String appId, IconURL iconURL);

}
