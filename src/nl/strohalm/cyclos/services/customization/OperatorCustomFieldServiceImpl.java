/*
    This file is part of Cyclos (www.cyclos.org).
    A project of the Social Trade Organisation (www.socialtrade.org).

    Cyclos is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    Cyclos is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Cyclos; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

 */
package nl.strohalm.cyclos.services.customization;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.customization.fields.OperatorCustomField;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.members.Operator;
import nl.strohalm.cyclos.utils.cache.CacheCallback;
import nl.strohalm.cyclos.utils.validation.Validator;

/**
 * Implementation for {@link OperatorCustomFieldServiceLocal}
 * 
 * @author luis
 */
public class OperatorCustomFieldServiceImpl extends BaseCustomFieldServiceImpl<OperatorCustomField> implements OperatorCustomFieldServiceLocal {

    protected OperatorCustomFieldServiceImpl() {
        super(OperatorCustomField.class);
    }

    public Validator getValueValidator(final Member member) {
        return getValueValidator(list(member));
    }

    public List<OperatorCustomField> list(final Member member) {
        return getCache().get("_FIELDS_BY_MEMBER_" + member.getId(), new CacheCallback() {
            public Object retrieve() {
                return customFieldDao.listOperatorFields(member);
            }
        });
    }

    public void saveValues(final Operator operator) {
        getValueValidator(operator.getMember()).validate(operator);
        doSaveValues(operator);
    }

    @Override
    protected List<OperatorCustomField> list(final OperatorCustomField field) {
        return list(field.getMember());
    }

    @Override
    protected Collection<? extends Relationship> resolveAdditionalFetch() {
        return Arrays.asList(OperatorCustomField.Relationships.MEMBER);
    }
}
