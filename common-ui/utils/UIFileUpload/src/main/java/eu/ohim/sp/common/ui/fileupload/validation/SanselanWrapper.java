/*******************************************************************************
 * * $Id:: SanselanWrapper.java 113489 2013-04-22 14:59:26Z karalch              $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.fileupload.validation;

import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;

import java.io.IOException;

/**
 * @author ionitdi
 */
public class SanselanWrapper
{
    public ImageInfo getImageInfo(byte[] image) throws IOException, ImageReadException
    {
        return Sanselan.getImageInfo(image);
    }

    public IImageMetadata getMetadata(byte[] image) throws IOException, ImageReadException
    {
        return Sanselan.getMetadata(image);
    }
}
