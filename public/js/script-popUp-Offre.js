		//--------------------------------Pop up add Admin------------------------------------------------------
		const openButtonAdmin = document.getElementById('open-popup-Admin');
		const closeButtonAdmin = document.getElementById('close-popup-Admin');
		const popupContainerAdmin = document.getElementById('popup-container-Admin');
		const popupOverlayAdmin = document.getElementById('popup-overlay-Admin');

    const openButtonAdminUpdate = document.getElementById('open-popup-AdminUpdate');
		const closeButtonAdminUpdate = document.getElementById('close-popup-AdminUpdate');
		const popupContainerAdminUpdate = document.getElementById('popup-container-AdminUpdate');
		const popupOverlayAdminUpdate = document.getElementById('popup-overlay-AdminUpdate');
    
    function openPopUp()
    {
      setTimeout(() => {
        popupContainerAdmin.style.opacity = '0';
        popupContainerAdmin.style.transform='translate(-50%, -50%) scale(0)';
        popupContainerAdmin.style.display = 'block';
        popupOverlayAdmin.style.display = 'block';
        // Add an animation to the popup window using CSS transitions
        setTimeout(() => {
            popupContainerAdmin.style.opacity = '1';
            popupContainerAdmin.style.transform='translate(-50%, -50%) scale(1)';
        }, 200); // Wait for 50ms before starting the animation
    }, 200);
    }

    function openPopUpUpdate()
    {
      setTimeout(() => {
        popupContainerAdminUpdate.style.opacity = '0';
        popupContainerAdminUpdate.style.transform='translate(-50%, -50%) scale(0)';
        popupContainerAdminUpdate.style.display = 'block';
        popupOverlayAdmin.style.display = 'block';
        // Add an animation to the popup window using CSS transitions
        setTimeout(() => {
            popupContainerAdminUpdate.style.opacity = '1';
            popupContainerAdminUpdate.style.transform='translate(-50%, -50%) scale(1)';
        }, 200); // Wait for 50ms before starting the animation
    }, 200);
    }

		openButtonAdmin.addEventListener('click', () => {
	    // Add a delay of 500ms (0.5s) before showing the popup
      openPopUp();

		});

		closeButtonAdmin.addEventListener('click', () => {
      setTimeout(() => {
        popupContainerAdmin.style.opacity = '1';
        popupContainerAdmin.style.display = 'block';
        popupOverlayAdmin.style.display = 'block';
        popupContainerAdmin.style.transform='translate(-50%, -50%) scale(1)';
        setTimeout(() => {
            popupContainerAdmin.style.opacity = '0';
            popupContainerAdmin.style.display = 'none';
            popupOverlayAdmin.style.display = 'none';
            popupContainerAdmin.style.transform='translate(-50%, -50%) scale(0)';
        }, 100); 
    }, 150);
		});

    closeButtonAdminUpdate.addEventListener('click', () => {
      setTimeout(() => {
        popupContainerAdminUpdate.style.opacity = '1';
        popupContainerAdminUpdate.style.display = 'block';
        popupOverlayAdmin.style.display = 'block';
        popupContainerAdminUpdate.style.transform='translate(-50%, -50%) scale(1)';
        setTimeout(() => {
            popupContainerAdminUpdate.style.opacity = '0';
            popupContainerAdminUpdate.style.display = 'none';
            popupOverlayAdmin.style.display = 'none';
            popupContainerAdminUpdate.style.transform='translate(-50%, -50%) scale(0)';
        }, 100); 
    }, 150);
		});
   //-----------------------------------------------------------------
   const emailInput = document.querySelector('.email');
   const emailIcon = document.getElementById('email-icon');
   emailIcon.style.color ='#d9d9d9';
   emailInput.addEventListener('focus', () => {
     emailIcon.style.color = '#4A8292';
   });
 
   emailInput.addEventListener('blur', () => {
     emailIcon.style.color = '#d9d9d9';});
     //----------------------------------------------------------------
     const lastname = document.querySelector('.lastname');
     const userIcon = document.getElementById('name-icon1');
     userIcon.style.color ='#d9d9d9';
     lastname.addEventListener('focus', () => {
      userIcon.style.color = '#4A8292';
     });
   
     lastname.addEventListener('blur', () => {
      userIcon.style.color = '#d9d9d9';});
     //-----------------------------------------------------------------
     const nameInput = document.querySelector('.name');
     const nameIcon = document.getElementById('name-icon');
     nameIcon.style.color ='#d9d9d9';
     nameInput.addEventListener('focus', () => {
       nameIcon.style.color = '#4A8292';
     });
   
     nameInput.addEventListener('blur', () => {
       nameIcon.style.color = '#d9d9d9';});
       //-------------------------------------------------------
       const passwordInput = document.querySelector('.password');
       const passwordIcon = document.getElementById('password-icon');
        passwordIcon.style.color ='#d9d9d9';
       passwordInput.addEventListener('focus', () => {
         passwordIcon.style.color = '#4A8292';
       });
     
       passwordInput.addEventListener('blur', () => {
         passwordIcon.style.color = '#d9d9d9';});
         //----------------------------------------------------------
         const phoneNumberInput = document.querySelector('.phoneNumber');
         const phoneNumberIcon = document.getElementById('phoneNumber-icon');
         phoneNumberIcon.style.color ='#d9d9d9';
         phoneNumberInput.addEventListener('focus', () => {
           phoneNumberIcon.style.color = '#4A8292';
         });
       
         phoneNumberInput.addEventListener('blur', () => {
           phoneNumberIcon.style.color = '#d9d9d9';});
           // Open the popup

        function callEdit(id)
        {
          const obj = {idOffre : id}
          alert(obj);
          $.ajax({  
            url: "/offre/offreData",  
            type: 'POST',   
            dataType: 'json',  
            data: obj,
            async: true,  
            
            success: function(data, status) {
              console.log(data);
                 openPopUpUpdate();
                 
                 document.offre_update.offre_update_id.value = data['idOffre'];
                 document.offre_update.offre_update_description.value = data['description']; 
                 document.offre_update.offre_update_niveau.value = data['niveau'];
                 document.offre_update.offre_update_dateDebut.value = data['dateDeb'];   
            },  
            error : function(xhr, textStatus, errorThrown) {  
                 console.log(xhr);
               alert(textStatus+' Ajax request failed.'+ errorThrown+" "+xhr);  
            }  
         });  
        }