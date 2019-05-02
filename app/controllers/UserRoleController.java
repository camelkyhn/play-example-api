package controllers;

import middleware.Attributes.Secured;
import middleware.Dtos.UserRoleDto;
import middleware.Bases.BaseController;
import middleware.Bases.IController;
import middleware.Core.GeneralResult;
import middleware.Entities.UserRole;
import middleware.Exceptions.IdCanNotBeEmptyException;
import middleware.Exceptions.InvalidModelStateException;
import middleware.Filters.UserRoleFilter;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;
import services.UserRole.UserRoleService;

import javax.inject.Inject;
import java.util.List;

@Security.Authenticated(Secured.class)
public class UserRoleController extends BaseController implements IController {

    private FormFactory _formFactory;
    private UserRoleService _userRoleService;

    @Inject
    public UserRoleController(FormFactory formFactory, UserRoleService userRoleService) {
        _formFactory     = formFactory;
        _userRoleService = userRoleService;
    }

    @Override
    public Result detail(Long id) {
        if (id <= 0) {
            return errorResult(BAD_REQUEST, new IdCanNotBeEmptyException());
        }

        GeneralResult<UserRole> result = _userRoleService.detail(id);
        if (result.isFailed()) {
            return errorResult(NOT_FOUND, result);
        }

        return ok(Json.toJson(result));
    }

    @Override
    public Result list() {
        Form<UserRoleFilter> form = _formFactory.form(UserRoleFilter.class).bind(request().body().asJson());
        if (form.hasErrors()) {
            return errorResult(BAD_REQUEST, new GeneralResult(InvalidModelStateException.class.getSimpleName(), form.allErrors().get(0).key() + ": " + form.allErrors().get(0).message()));
        }

        GeneralResult<List<UserRole>> result = _userRoleService.list(form.get());
        if (result.isFailed()) {
            return errorResult(NOT_FOUND, result);
        }

        return ok(Json.toJson(result));
    }

    @Override
    public Result create() {
        Form<UserRoleDto> form = _formFactory.form(UserRoleDto.class).bind(request().body().asJson());
        if (form.hasErrors()) {
            return errorResult(BAD_REQUEST, new GeneralResult(InvalidModelStateException.class.getSimpleName(), form.allErrors().get(0).key() + ": " + form.allErrors().get(0).message()));
        }

        GeneralResult<Boolean> result = _userRoleService.save(form.get());
        if (result.isFailed()) {
            return errorResult(INTERNAL_SERVER_ERROR, result);
        }

        return ok(Json.toJson(result));
    }

    @Override
    public Result edit() {
        Form<UserRoleDto> form = _formFactory.form(UserRoleDto.class).bind(request().body().asJson());
        if (form.hasErrors()) {
            return errorResult(BAD_REQUEST, new GeneralResult(InvalidModelStateException.class.getSimpleName(), form.allErrors().get(0).key() + ": " + form.allErrors().get(0).message()));
        }

        GeneralResult<Boolean> result = _userRoleService.update(form.get());
        if (result.isFailed()) {
            return errorResult(INTERNAL_SERVER_ERROR, result);
        }

        return ok(Json.toJson(result));
    }

    @Override
    public Result delete(Long id) {
        if (id <= 0) {
            return errorResult(BAD_REQUEST, new IdCanNotBeEmptyException());
        }

        GeneralResult<Boolean> result = _userRoleService.delete(id);
        if (result.isFailed()) {
            return errorResult(NOT_FOUND, result);
        }

        return ok(Json.toJson(result));
    }
}