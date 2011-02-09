package com.canarylogic.focalpoint

class AlphaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
		log.debug params
        [alphaInstanceList: Alpha.list(params), alphaInstanceTotal: Alpha.count()]
    }

    def create = {
        def alphaInstance = new Alpha()
        alphaInstance.properties = params
        return [alphaInstance: alphaInstance]
    }

    def save = {
        def alphaInstance = new Alpha(params)
        if (alphaInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'alpha.label', default: 'Alpha'), alphaInstance.id])}"
            redirect(action: "show", id: alphaInstance.id)
        }
        else {
            render(view: "create", model: [alphaInstance: alphaInstance])
        }
    }

    def show = {
        def alphaInstance = Alpha.get(params.id)
        if (!alphaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'alpha.label', default: 'Alpha'), params.id])}"
            redirect(action: "list")
        }
        else {
            [alphaInstance: alphaInstance]
        }
    }

    def edit = {
        def alphaInstance = Alpha.get(params.id)
        if (!alphaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'alpha.label', default: 'Alpha'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [alphaInstance: alphaInstance]
        }
    }

    def update = {
        def alphaInstance = Alpha.get(params.id)
        if (alphaInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (alphaInstance.version > version) {
                    
                    alphaInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'alpha.label', default: 'Alpha')] as Object[], "Another user has updated this Alpha while you were editing")
                    render(view: "edit", model: [alphaInstance: alphaInstance])
                    return
                }
            }
            alphaInstance.properties = params
            if (!alphaInstance.hasErrors() && alphaInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'alpha.label', default: 'Alpha'), alphaInstance.id])}"
                redirect(action: "show", id: alphaInstance.id)
            }
            else {
                render(view: "edit", model: [alphaInstance: alphaInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'alpha.label', default: 'Alpha'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def alphaInstance = Alpha.get(params.id)
        if (alphaInstance) {
            try {
                alphaInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'alpha.label', default: 'Alpha'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'alpha.label', default: 'Alpha'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'alpha.label', default: 'Alpha'), params.id])}"
            redirect(action: "list")
        }
    }
}
