package cs3500.animator.provider.view.interfaces;

import java.io.IOException;

/**
 * An interface that allows users to export their views.
 */
public interface ExportView {

  void export(String fileToExportTo) throws IOException;

}
