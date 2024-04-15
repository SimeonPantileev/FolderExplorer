function downloadFile(fileId) {
    let url = `/download/` + fileId;

    fetch(url, {
        method: 'GET'
    })
        .then(response => {
            if (response.ok) return response.blob();
            throw new Error('Network response was not ok.');
        }).then(blob => {
        // Create a link element
        const link = document.createElement('a');
        const url = URL.createObjectURL(blob);
        link.href = url;
        link.download = 'downloaded_file'; // You can set a default name or get it from the content-disposition header

        // Append the link to the body, click it, and then remove it
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);

        // Revoke the object URL after a while to free up resources
        setTimeout(() => URL.revokeObjectURL(url), 1000);
    })
        .catch(e => console.error('Error downloading file:', e));
}

// window.addEventListener('DOMContentLoaded', () => {
    document.getElementById('dropArea').addEventListener('dragover', function (event) {
        event.stopPropagation();
        event.preventDefault();
        event.dataTransfer.dropEffect = 'copy';
    });

    document.getElementById('dropArea').addEventListener('drop', function (event) {
        event.stopPropagation();
        event.preventDefault();
        const files = event.dataTransfer.files;
        uploadFile(files[0]);
    });
// })

function uploadFile(file) {

    let formData = new FormData();
    formData.append('file', file)
    let fileName = document.getElementById(`fileName`).value;
    let pathParts = window.location.pathname.split(`/`);
    let id = pathParts[2];
    console.log(id);

    formData.append('file', file);

    formData.append(`fileName`, fileName);


    fetch("upload", {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (response.ok) {
                window.location.href = `/folder/${id}`;
            } else {
                throw new Error('Upload failed');
            }
        })
        .catch(error => {
            console.error(error);
        });
}

function renameFile() {
    document.getElementById(`rename_form`).submit();
}

function renameFolder() {
    document.getElementById(`rename_form`).submit();
}

function addFolder() {
    document.getElementById(`create_form`).submit();
}

