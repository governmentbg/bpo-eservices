//TODO some parts of the script are specific to design views. Make it more generic so it could be used elsewhere too
var uploadViews = {
    views: [],
    addViewController: {
        fn : this,
        init: function(){
            if(myDropzone) return;
            var url = $('#dragAndDropUpload').data('url');
            var that = this;
            var $save = $('#saveDesignViewsBtn');
            var filesAlreadySaved  = $('#numUploadEntities').attr("value");
            var maxFiles = $('#maximumEntities').attr("value");

            this.fn.uploadViews.views = [];
            this.checkboxEvents();

            $('.deleteModal, .editModal').hide();
            $('.viewModal').show();
            var myDropzone = new Dropzone("#dropzone", {
                url: url,
                clickable: true,
                paramName: 'view',
                maxFiles: maxFiles-filesAlreadySaved,
                init: function() {
                    //Init save button disabled no uploads
                    $save.prop("disabled",true).addClass('disabled');
                    that.fn.uploadViews.utils.counter.refresh(filesAlreadySaved, maxFiles)

                    this.on("complete", function() {
                        var uploading = true;
                        $.each( this.files , function( i, l ){
                            uploading = (l.status === "uploading") ? true : false;
                        });
                        if (!uploading && this.files.length){
                            $save.prop("disabled", false).removeClass('disabled');
                        }
                        that.fn.uploadViews.utils.counter.refresh(filesAlreadySaved, maxFiles)

                    });
                    this.on("addedfile", function(file) {
                        if (that.fn.uploadViews.utils.checkUniqueness(file.name)) {
                            this.removeFile(file);
                            var error = $('#errorRepeatedImage').html();
                            that.fn.uploadViews.utils.showAlertError(error, file.name);
                        }
                    });

                },
                sending: function(file, xhr, formData){
                    $("#formDragAndDropUpload").find("inputmock").each(function (index) {
                        formData.append($(this).attr("name"), $(this).attr("value"));
                    });
                    $save.prop("disabled",true).addClass('disabled');
                },
                success: function(file, response) {
                    var $file = $(file.previewElement);
                    //Error
                    if($('<div>'+response+'<div>').find('.flMessageError').length){
                        this.removeFile(file);
                        var content =$('<div>'+response+'<div>').find('.flMessageError').html();
                        that.fn.uploadViews.utils.showAlertError(content,file.name);
                    }else{ //Success
                        $file.append(response);
                        $file.find('.dz-type').html($file.find('#contentType').attr('value'));
                        that.fn.uploadViews.utils.addForm($file,$file.find('#documentId').attr('value'));

                        //Add dropzone to change only one
                        var id = 'file'+$file.find('inputmock#documentId').attr('value');
                        $file.find('.change-image img').attr('id', id);
                        that.fn.uploadViews.utils.editUploadedFile(id, that.fn.uploadViews.utils.createImage);

                        that.fn.uploadViews.views = this.files.slice(0);

                        return $file.addClass("dz-success");
                    }
                },
                error: function(file, response){
                    var $container = $('#uploadFilesErrorContainer')
                    this.removeFile(file);
                    that.fn.uploadViews.utils.showAlertError(response,file.name );
                },
                previewTemplate: document.querySelector('#tpl').innerHTML,
                previewsContainer: document.querySelector('#previewFiles')
            });
        },
        checkboxEvents:function () {
            $('#designViewSection.modal').on('change', '.correspondence-address input[type="checkbox"]', function(e){
                var node = $(e.currentTarget);
                var isChecked = node.prop('checked');
                var otherNode = node.closest('.form-container').find('input[type="checkbox"]').not(node);
                if(isChecked) {
                    otherNode.prop('disabled', true);
                } else {
                    otherNode.prop('disabled', false);
                }
            });

            var disableCheckbox = function (otherNode, status){
                otherNode.prop('disabled', status);
            };
        }
    },
    editViewController: {
        fn: this,
        views: {},
        init : function(sequence, isDelete, isView){
            var that = this;
            this.views = {};
            this.fn.uploadViews.views = [];

            $('.viewModal, .deleteModal, .editModal').hide();
            $('#dropzone').hide();

            $('.deleteModal').toggle(isDelete);
            $('.editModal').toggle(!isDelete && !isView);
            $('.viewViewsModal').toggle(isView);


            $("#editMultiupload").find("inputmock").each(function (index) {
                var sequence = $(this).data('sequence');
                var name = $(this).attr('name');
                var value = $(this).attr('value');
                that.views[sequence] = that.views[sequence] || {};

                if($(this).hasClass('thumbnail')){
                    that.views[sequence]['thumbnail'] = that.views[sequence]['thumbnail'] || {};
                    that.views[sequence]['thumbnail'][name] = value;
                }else if($(this).hasClass('form')){
                    that.views[sequence]['form'] = that.views[sequence]['form'] || {};
                    that.views[sequence]['form'][name] = value;
                }else{
                    that.views[sequence][name] = value;
                }
            });

            for (var key in this.views) {
                this.fn.uploadViews.views.push({name: this.views[key].originalFilename});
            }

            that.printViews(sequence, isDelete, isView);
        },
        printViews: function(selected, isDelete, isView){
            var that = this;
            var form = $('#fileUploadTemplate').html();
            var previewContainer = $('#previewFiles');
            var tpl = $('#tpl-edit').html();
            for (var key in this.views) {
                previewContainer.append(tpl);
                var file = previewContainer.find('.dz-preview:last-child')
                var documentId = this.views[key]['thumbnail'].documentId;

                that.fn.uploadViews.utils.createImage(
                    file,
                    documentId,
                    this.views[key].originalFilename,
                    this.views[key].formattedFileSize,
                    this.views[key].contentType
                );


                that.fn.uploadViews.utils.addForm(file,documentId);

                if(!isDelete && !isView ){
                    var id = 'file'+documentId;
                    file.find('.change-image img').attr('id', id)
                    that.fn.uploadViews.utils.editUploadedFile(id, that.fn.uploadViews.utils.createImage);
                }

                if(this.views[key].sequence === selected) file.addClass('selected');

                var formContainer = file.find('.form-container');

                for (var keyForm in this.views[key].form) {
                    formContainer.find('textarea[name*='+keyForm+']').attr('value', this.views[key].form[keyForm]);
                    formContainer.find('input[type=text][name*='+keyForm+']').attr('value', this.views[key].form[keyForm]);
                    formContainer.find('input[type=hidden][name*='+keyForm+']').attr('value', this.views[key].form[keyForm]);
                    formContainer.find('select[name*='+keyForm+']').attr('value', this.views[key].form[keyForm]);
                    formContainer.find('input[type=checkbox][name*='+keyForm+']').prop('checked', (this.views[key].form[keyForm] == "true"));
                }
                $('<input>', {
                    type: 'hidden',
                    name: 'sequence',
                    value: this.views[key].sequence
                }).appendTo(formContainer);

                if(isDelete || isView){
                    file.find('.change-image').remove();
                    formContainer.find('input, select, textarea').prop("disabled", true);
                    formContainer.find('label').addClass('disabled');
                    if(isDelete){
                        var checkContainer = file.find('.select-input').addClass('visible');
                        if(this.views[key].sequence === selected) checkContainer.find('input').prop('checked', true);
                        checkContainer.find('input').change( function(){
                            that.selectView($(this));
                        })
                    }

                }else{
                    formContainer.append('<inputmock id="documentId" value="'+this.views[key].documentId+'"></inputmock>');
                }
            }
        },
        scrollSelected:function(){
            var $selectedItem = $('#previewFiles').find('.dz-preview.selected');
            if($selectedItem.length){
                var $parentDiv = $('#designViewSection .modal-body');
                var top = $parentDiv.scrollTop() + $selectedItem.position().top
                $parentDiv.stop().animate({scrollTop:top}, 1000, 'swing');
            }
        },
        selectView : function (el){
            el.closest(".dz-preview").toggleClass( 'selected', el.prop('checked') );
            var $selectedItem = $('#previewFiles').find('.dz-preview.selected');
            $('#deleteDesignViewsBtn').toggleClass('disabled', !$selectedItem.length).prop('disabled',!$selectedItem.length);
        },
        getSelectedViews: function(){
            var data = [];
            $(".dz-preview.selected").each(function(){
                var sequence = $(this).find('input:hidden[name*=sequence]').attr('value');
                if(sequence) data.push(sequence);
            })
            return data.join(";");
        }
    },
    utils: {
        fn: this,
        getFilesData: function(){
            var data = {};
            data.designViewForms = [];
            $('#previewFiles').find(".dz-preview").each(function (index) {
                var $this = $(this);
                var storedFiles =  {}
                var thumbnail = {}

                var sequenceFile = $this.find('input:hidden[name*=sequence]');

                if(sequenceFile.length){
                    $("#editMultiupload").find("inputmock").each(function (index) {
                        var sequence = $(this).data('sequence');
                        var name = $(this).attr('name');
                        var value = $(this).attr('value');
                        if(sequence == sequenceFile.attr('value')){
                            if($(this).hasClass('file')){
                                storedFiles[name] =  value;
                            }

                            if($(this).hasClass('thumbnail')){
                                thumbnail[$(this).attr("name")] =  $(this).attr("value");
                            }
                        }
                    })
                }else{
                    $this.find("inputmock").each(function (index) {
                        if($(this).hasClass('file')){
                            storedFiles[$(this).attr("id")] =  $(this).attr("value");
                        }

                        if($(this).hasClass('thumbnail')){
                            thumbnail[$(this).attr("name")] =  $(this).attr("value");
                        }
                    })
                }

                storedFiles.thumbnail = thumbnail;
                var view = {storedFiles: [storedFiles]}

                var form = $this.find('form').serializeFormJSON();
                form.view = view;

                var formData = $this.find('.form-container');
                formData.find('textarea, input, select').each(function (index) {
                    var name = $(this).attr('name').replace('designs[0].', '');
                    if($(this).attr('type') !== 'checkbox'){
                        form[name] = $(this).attr("value");
                    }else{
                        var checkboxVal = $(this).is(':checked');
                        form[name] = checkboxVal;
                    }
                })

                data.designViewForms.push(form);
            });
            return JSON.stringify(data);
        },
        editUploadedFile: function(id,callback){
            var url = $('#dragAndDropUpload').data('url');
            var that = this;
            new Dropzone('#'+id, {
                url: url,
                clickable: true,
                paramName: 'view',
                init: function() {
                    this.hiddenFileInput.removeAttribute('multiple');

                    this.on("addedfile", function(file) {
                        if (that.fn.uploadViews.utils.checkUniqueness(file.name)) {
                            this.removeFile(file);
                            var errorContent = $('#errorRepeatedImage').html();
                            var parent = $('#'+id).parents()[2];
                            $(parent).find('.form-container').prepend('<div class="flMessageError"><span class="title">'+file.name+'</span>'+errorContent+'</div>');
                        }
                    });
                },
                sending: function(file, xhr, formData){
                    $("#formDragAndDropUpload").find("inputmock").each(function (index) {
                        formData.append($(this).attr("name"), $(this).attr("value"));
                    });
                },
                success : function(fileData, response){
                    var $element = $(this.element);
                    var $container = $($element.parents()[2]);
                    this.removeFile(fileData);
                    var name = $container.find('inputmock#originalFilename').attr('value');
                    if(!name) name = $container.find('.dz-filename span').html();
                    //Error
                    if($('<div>'+response+'<div>').find('.flMessageError').length){
                        $container.find('.form-container').prepend(response);
                    }else{ //Success
                        that.fn.uploadViews.utils.changeView(name, fileData);
                        var sequence = $container.find('input[name*=sequence]').attr('value');
                        var imageData = [];
                        var editData =$('#editMultiupload');

                        var data = $('<div>'+response+'<div>').find('inputmock').each(function(){
                            if($(this).hasClass('file')){
                                var name = $(this).attr('id');
                                var value = $(this).attr('value');
                                if(sequence){
                                    editData.find('inputmock.file[name*='+name+']').each(function(){
                                        if($(this).data('sequence') == sequence) $(this).attr('value', value);
                                    })
                                }else{
                                    $container.find('inputmock#'+name).attr('value', value);
                                }

                                imageData[name] = value;
                            } else if($(this).hasClass('thumbnail')){
                                var name = $(this).attr('name');
                                var value = $(this).attr('value');
                                if(sequence){
                                    editData.find('inputmock.thumbnail[name*='+name+']').each(function(){
                                        if($(this).data('sequence') == sequence) $(this).attr('value', value);
                                    })
                                }else{
                                    $container.find('inputmock.thumbnail[name*='+name+']').attr('value', value);
                                }
                            }
                        })

                        callback(
                            $container,
                            imageData['documentId'],
                            imageData['originalFilename'],
                            imageData['formattedFileSize'],
                            imageData['contentType']
                        )

                    }
                },
                previewTemplate: document.querySelector('#tpl').innerHTML,
                previewsContainer: document.querySelector('#uploadDiv')
            })

        },
        checkUniqueness: function(name){
            var sameName = this.fn.uploadViews.views.filter(function(el){
                return name === el.name;
            })
            return sameName.length >= 1;
        },
        showErrorsForm: function(html) {
            var errors = $('<div>'+html+'<div>').find('.flMessageError');
            $('#previewFiles').find(".dz-preview .flMessageError").remove();
            $('#previewFiles').find("l .error").remove();
            errors.each(function(index){
                var data = $(this).data('file').split("_");
                var msg = $(this).html();
                $('#previewFiles').find(".dz-preview").each(function (index) {
                    var id = $(this).find('inputmock#documentId').attr('value');
                    if(id === data[1]){
                        $(this).find('label.error').hide();
                        if(data[0] === "xssAttack"){
                            var field = $(this).find("*[class='form-container']").children().first();
                            field.before('<div class="flMessageError">'+msg+"</div>");
                        }
                        else{
                            var field = $(this).find("*[name='"+data[0]+"']");
                            field.after('<div class="flMessageError">'+msg+"</div>");
                            if(field.hasClass('mandatory')) field.addClass('error');
                        }
                    }
                })
            })
        },
        showAlertError: function(content, title){
            $('#uploadFilesErrorContainer').append('<div class="flMessageError"><span class="title">'+title+'</span>'+content+'</div>');
            var $alert = $('#uploadFilesErrorContainer').find('.flMessageError:last-child');
            $alert.append('<span class="close">x</span>');
            $alert.find('.close').on('click', function(){
                $alert.fadeOut(1000, function() { $(this).remove(); });
            });
            setTimeout(function(){
                $alert.fadeOut(1000, function() { $(this).remove(); });
            },10000)
        },
        changeView: function(oldFileName, newFile){
            var views = this.fn.uploadViews.views.map(function(el){
                if(oldFileName === el.name){
                    return newFile;
                }
                return el;
            })

            this.fn.uploadViews.views = views.slice(0);
        },
        createImage: function(file,documentId, name, size, contentType){
            var tmpImg = new Image() ;
            tmpImg.src = 'getDocument.htm?documentId='+ documentId;
            tmpImg.container = file.find('.dz-details');
            tmpImg.onload = function() {
                this.container.find('img').not( ".edit-icon" ).remove();
                this.container.prepend('<img src="'+CropImage.crop(this)+'" />');

            }

            file.find('.dz-filename span').html(name);
            file.find('.dz-size').html(size);
            file.find('.dz-type').html(contentType);
        },
        counter:{
            that: this,
            refresh:function(filesAlreadySaved, maxFiles){
                var status = ((this.that.uploadViews.views.length + +filesAlreadySaved) >= maxFiles ) ? 'error' : 'success';
                var current = '<span class="'+status+'">'+(this.that.uploadViews.views.length + +filesAlreadySaved)+'</span>';
                current += '/<span class="error">'+maxFiles+'</span>';
                $('#counter .result').html(current);
            }
        },
        addForm: function(file, id){
            var form = $('#fileUploadTemplate').html();
            var formContainer = file.find('.form-container');
            formContainer.append(form);
            formContainer.find('input, textarea, select').each(function(){
                var oldId = $(this).attr('id');
                var newId = oldId+id;
                $(this).attr('id', newId);
                formContainer.find("label[for="+oldId+"]").attr('for', newId);
            })
        }
    }
}


//Crop image
var CropImage = {
    crop: function(img){
        var _this = this;
        var file = {};

        file.width = img.width;
        file.height = img.height;

        var resizeInfo = _this.resize.call(_this, file, 120, 120,  'crop');

        var canvas = document.createElement("canvas");
        var ctx = canvas.getContext("2d");

        canvas.width = resizeInfo.trgWidth;
        canvas.height = resizeInfo.trgHeight;

        // This is a bugfix for iOS' scaling bug.
        drawImageIOSFix(ctx, img, resizeInfo.srcX != null ? resizeInfo.srcX : 0, resizeInfo.srcY != null ? resizeInfo.srcY : 0, resizeInfo.srcWidth, resizeInfo.srcHeight, resizeInfo.trgX != null ? resizeInfo.trgX : 0, resizeInfo.trgY != null ? resizeInfo.trgY : 0, resizeInfo.trgWidth, resizeInfo.trgHeight);

        return canvas.toDataURL("image/png");

    },
    drawImageIOSFix : function (ctx, img, sx, sy, sw, sh, dx, dy, dw, dh) {
        var vertSquashRatio = detectVerticalSquash(img);
        return ctx.drawImage(img, sx, sy, sw, sh, dx, dy, dw, dh / vertSquashRatio);
    },
    detectVerticalSquash : function (img) {
        var iw = img.naturalWidth;
        var ih = img.naturalHeight;
        var canvas = document.createElement("canvas");
        canvas.width = 1;
        canvas.height = ih;
        var ctx = canvas.getContext("2d");
        ctx.drawImage(img, 0, 0);

        var _ctx$getImageData = ctx.getImageData(1, 0, 1, ih),
            data = _ctx$getImageData.data;

        // search image edge pixel position in case it is squashed vertically.


        var sy = 0;
        var ey = ih;
        var py = ih;
        while (py > sy) {
            var alpha = data[(py - 1) * 4 + 3];

            if (alpha === 0) {
                ey = py;
            } else {
                sy = py;
            }

            py = ey + sy >> 1;
        }
        var ratio = py / ih;

        if (ratio === 0) {
            return 1;
        } else {
            return ratio;
        }
    },
    resize: function (file, width, height, resizeMethod) {
        var info = {
            srcX: 0,
            srcY: 0,
            srcWidth: file.width,
            srcHeight: file.height
        };

        var srcRatio = file.width / file.height;

        // Automatically calculate dimensions if not specified
        if (width == null && height == null) {
            width = info.srcWidth;
            height = info.srcHeight;
        } else if (width == null) {
            width = height * srcRatio;
        } else if (height == null) {
            height = width / srcRatio;
        }

        // Make sure images aren't upscaled
        width = Math.min(width, info.srcWidth);
        height = Math.min(height, info.srcHeight);

        var trgRatio = width / height;

        if (info.srcWidth > width || info.srcHeight > height) {
            // Image is bigger and needs rescaling
            if (resizeMethod === 'crop') {
                if (srcRatio > trgRatio) {
                    info.srcHeight = file.height;
                    info.srcWidth = info.srcHeight * trgRatio;
                } else {
                    info.srcWidth = file.width;
                    info.srcHeight = info.srcWidth / trgRatio;
                }
            } else if (resizeMethod === 'contain') {
                // Method 'contain'
                if (srcRatio > trgRatio) {
                    height = width / srcRatio;
                } else {
                    width = height * srcRatio;
                }
            } else {
                throw new Error("Unknown resizeMethod '" + resizeMethod + "'");
            }
        }

        info.srcX = (file.width - info.srcWidth) / 2;
        info.srcY = (file.height - info.srcHeight) / 2;

        info.trgWidth = width;
        info.trgHeight = height;

        return info;
    }

}