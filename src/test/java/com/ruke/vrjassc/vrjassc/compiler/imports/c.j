import "./src/test/java/com/ruke/vrjassc/vrjassc/compiler/imports/a.j"
import "./src/test/java/com/ruke/vrjassc/vrjassc/compiler/imports/b.j"

struct c implements b, a
    public method foo returns a
        return this
    end
end