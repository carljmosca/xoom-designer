import FormModal from "../FormModal";
import useFormHandler from "../../utils/FormHandler";
import {useCallback} from "react";
import axios from "axios";
<#macro printFormElement name type>
    <#if valueTypes[type]??>
        <#list valueTypes[type] as subType>
            <@printFormElement "${name}.${subType.name}" subType.type/>
        </#list>
    <#else>
      <div className='mb-3'>
        <label htmlFor='${name}' className={'form-label text-capitalize'}>${fns.capitalizeMultiWord(name?replace('.', ' '))}</label>
        <input id='${name}' name={'${name}'} required={true} value={form.${name}} onChange={onFormValueChange} className={'form-control form-control-sm'}/>
      </div>
    </#if>
</#macro>

<#macro formatFormElement name type>
  <#if valueTypes[type]??>
    if(Array.isArray(form.${name}))
      form.${name} = [Object.assign({}, form.${name})]
  <#elseif aggregate.stateFields?filter(field -> field.name == name)?first.isCollection>
    form.${name} = [form.${name}]
  </#if>
</#macro>

const applyData = (uri, data) => {
  return uri.replace(/(?:{(.+?)})/g, x => data[x.slice(1,-1)]);
}

const ${fns.capitalize(aggregate.aggregateName)}${fns.capitalize(method.name)} = ({id = null, defaultForm, complete}) => {

  const [form, onFormValueChange] = useFormHandler(defaultForm);

  const submit = useCallback((e) => {
<#list method.parameters as p>
<@formatFormElement p fieldTypes[p]/>
</#list>

    const url = applyData('${route.path}', form);
<#if route.httpMethod?length == 0>
    axios.post(url, form)
  <#else>
    axios.${route.httpMethod?lower_case}(url, form)
</#if>
    .then(res => res.data)
    .then(data => {
      complete(data);
      console.log('${aggregate.aggregateName}${fns.capitalize(method.name)} axios complete', data);
    })
    .catch(e => {
      console.error('${aggregate.aggregateName}${fns.capitalize(method.name)} axios failed', e);
    });
  }, [form, complete]);

  const close = useCallback((e) => {
    complete();
  }, [complete]);

  return (
    <>
      <FormModal title={"${fns.capitalize(method.name)}"} show={true} close={close} submit={submit}>
        <#list method.parameters as p>
          <@printFormElement p fieldTypes[p] />
        </#list>
      </FormModal>
    </>
  )
};

export default ${fns.capitalize(aggregate.aggregateName)}${fns.capitalize(method.name)};
