package finance.defi.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class EntityNotFoundException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public EntityNotFoundException() {
        super(ErrorConstants.ENTITY_NOT_FOUND_TYPE, "Entity not found", Status.BAD_REQUEST);
    }

    public EntityNotFoundException(String message) {
        super(ErrorConstants.ENTITY_NOT_FOUND_TYPE, message, Status.BAD_REQUEST);
    }
}
