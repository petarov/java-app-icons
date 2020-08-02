package net.vexelon.appicons.wireframe;

import net.vexelon.appicons.entities.URLIcon;

import java.io.InputStream;
import java.util.List;

public interface IconParser {

    List<URLIcon> parse(InputStream input);

}
