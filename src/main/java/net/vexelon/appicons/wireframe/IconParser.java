package net.vexelon.appicons.wireframe;

import net.vexelon.appicons.wireframe.entities.IconURL;

import java.io.InputStream;
import java.util.List;

public interface IconParser {

    List<IconURL> parse(InputStream input);

}
