import to.MaterialTo;
import to.RootModelTo;
import to.TechModelTo;

import java.util.ArrayList;
import java.util.List;

public class ViewModelsConverter {

    // Сюда приходит список:  TECHNOLOGY,MATERIAL,MATERIAL,..,MATERIAL
    public static TechModelTo toTechModel(List<RowModel> source) {
        TechModelTo result = null;
        for (RowModel rowModel : source) {
            switch (rowModel.positionType) {
                case ROOT -> throw new IllegalArgumentException();
                case TECHNOLOGY -> {
                    if (result != null) throw new IllegalArgumentException();
                    result = new TechModelTo(rowModel.anyCode);
                }
                case MATERIAL -> {
                    if (result == null) throw new IllegalArgumentException();
                    result.rowTos.add(new MaterialTo(rowModel.anyCode));
                }
            }
        }
        return result;
    }

    // Сюда приходит список:  ROOT,TECHNOLOGY,MATERIAL,MATERIAL,..,MATERIAL, [ROOT,TECHNOLOGY,MATERIAL,MATERIAL,..,MATERIAL], ...
    public static List<RootModelTo> toRootModels(List<RowModel> source) {
        List<RootModelTo> roots = new ArrayList<>();
        if (source.isEmpty()) {
            return roots;
        }
        RootModelTo rootModelTo = null;
        TechModelTo techModelTo = null;
        for (RowModel rowModel : source) {
            switch (rowModel.positionType) {
                case ROOT -> {
                    if (rootModelTo != null) roots.add(rootModelTo);
                    rootModelTo = new RootModelTo(rowModel.anyCode);
                }
                case TECHNOLOGY -> {
                    if (rootModelTo == null) throw new IllegalArgumentException();
                    if (techModelTo != null) rootModelTo.techList.add(techModelTo);
                    techModelTo = new TechModelTo(rowModel.anyCode);
                }
                case MATERIAL -> {
                    if (techModelTo == null) throw new IllegalArgumentException();
                    techModelTo.rowTos.add(new MaterialTo(rowModel.anyCode));
                }
            }
        }
        return roots;
    }
}
