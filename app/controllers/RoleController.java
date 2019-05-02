package controllers;

import middleware.Attributes.Secured;
import middleware.Dtos.RoleDto;
import middleware.Bases.BaseController;
import middleware.Bases.IController;
import middleware.Core.GeneralResult;
import middleware.Entities.Role;
import middleware.Exceptions.IdCanNotBeEmptyException;
import middleware.Exceptions.InvalidModelStateException;
import middleware.Filters.RoleFilter;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;
import services.Role.RoleService;

import javax.inject.Inject;
import java.util.List;

@Security.Authenticated(Secured.class)
public class RoleController extends BaseController implements IController {

    private FormFactory _formFactory;
    private RoleService _roleService;

    @Inject
    public RoleController(FormFactory formFactory, RoleService roleService) {
        _formFactory = formFactory;
        _roleService = roleService;
    }

    @Override
    public Result detail(Long id) {
        if (id <= 0) {
            return errorResult(BAD_REQUEST, new IdCanNotBeEmptyException());
        }

        GeneralResult<Role> result = _roleService.detail(id);
        if (result.isFailed()) {
            return errorResult(NOT_FOUND, result);
        }

        return ok(Json.toJson(result));
    }

    @Override
    public Result list() {
        Form<RoleFilter> form = _formFactory.form(RoleFilter.class).bind(request().body().asJson());
        if (form.hasErrors()) {
            return errorResult(BAD_REQUEST, new GeneralResult(InvalidModelStateException.class.getSimpleName(), form.allErrors().get(0).key() + ": " + form.allErrors().get(0).message()));
        }

        GeneralResult<List<Role>> result = _roleService.list(form.get());
        if (result.isFailed()) {
            return errorResult(NOT_FOUND, result);
        }

        return ok(Json.toJson(result));
    }

    @Override
    public Result create() {
        Form<RoleDto> form = _formFactory.form(RoleDto.class).bind(request().body().asJson());
        if (form.hasErrors()) {
            return errorResult(BAD_REQUEST, new GeneralResult(InvalidModelStateException.class.getSimpleName(), form.allErrors().get(0).key() + ": " + form.allErrors().get(0).message()));
        }

        GeneralResult<Boolean> result = _roleService.save(form.get());
        if (result.isFailed()) {
            return errorResult(INTERNAL_SERVER_ERROR, result);
        }

        return ok(Json.toJson(result));
    }

    @Override
    public Result edit() {
        Form<RoleDto> form = _formFactory.form(RoleDto.class).bind(request().body().asJson());
        if (form.hasErrors()) {
            return errorResult(BAD_REQUEST, new GeneralResult(InvalidModelStateException.class.getSimpleName(), form.allErrors().get(0).key() + ": " + form.allErrors().get(0).message()));
        }

        GeneralResult<Boolean> result = _roleService.update(form.get());
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

        GeneralResult<Boolean> result = _roleService.delete(id);
        if (result.isFailed()) {
            return errorResult(NOT_FOUND, result);
        }

        return ok(Json.toJson(result));
    }
}
