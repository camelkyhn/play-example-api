package middleware.Bases;

import middleware.Core.GeneralResult;

import java.util.List;

public interface IService<TKeyType, TEntity, TFilter, TDto> {

    GeneralResult<TEntity> detail(Long id);

    GeneralResult<List<TEntity>> list(TFilter filter);

    GeneralResult<Boolean> save(TDto dto);

    GeneralResult<Boolean> update(TDto dto);

    GeneralResult<Boolean> delete(TKeyType id);
}
