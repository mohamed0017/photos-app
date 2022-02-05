package com.task.koinztask.data.mapper

import com.task.koinztask.data.local.PhotoEntity
import com.task.koinztask.data.remote.Photo

class PhotoMapper : Mapper<Photo, PhotoEntity> {
    override fun map(d: Photo): PhotoEntity {
       return PhotoEntity(
            id = d.id,
           server = d.server,
           secret = d.secret,
           isFriend = d.isfriend,
           isFamily = d.isfamily,
           farm = d.farm,
           owner = d.owner,
           isPublic = d.ispublic,
           title = d.title
        )
    }
}