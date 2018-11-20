package manage.dataupdate.service.impl;

import base.database.DataBase;
import base.exceptions.DataAccessException;
import java.util.ArrayList;
import java.util.List;
import manage.dataupdate.service.DataService;
import manage.domain.pojo.DomainBean;
import manage.pipe.pojo.LedupInfoBean;
import manage.pipe.pojo.PipeInfoBean;
import manage.pipe.pojo.PipeSegmentInfoBean;
import manage.pipe.pojo.TubeInfoBean;
import manage.pipe.pojo.WellInfoBean;
import manage.poleline.pojo.PoleInfoBean;
import manage.poleline.pojo.PolelineInfoBean;
import manage.poleline.pojo.PolelineSegmentInfoBean;
import manage.poleline.pojo.SupportInfoBean;
import manage.poleline.pojo.SuspensionWireInfoBean;
import manage.poleline.pojo.SuspensionWireSegInfoBean;

public class DataServiceImpl extends DataBase
  implements DataService
{
  private static String GET_DOMAIN = "dataupdate.getDomain";

  private static String GET_PIPE = "dataupdate.getPipeList";
  private static String INSERT_PIPE = "dataupdate.insertPipe";

  private static String GET_PIPESEG = "dataupdate.getPipeSegList";
  private static String INSERT_PIPESEG = "dataupdate.insertPipeSeg";

  private static String GET_POLELINE = "dataupdate.getPolelineList";
  private static String INSERT_POLELINE = "dataupdate.insertPoleline";

  private static String GET_TUBE = "dataupdate.getTubeList";
  private static String INSERT_TUBE = "dataupdate.insertTube";

  private static String INSERT_POLE = "dataupdate.insertPole";
  private static String GET_POLE = "dataupdate.getPoleList";

  private static String INSERT_WELL = "dataupdate.insertWell";
  private static String GET_WELL = "dataupdate.getWellList";

  private static String INSERT_LEDUP = "dataupdate.insertLedup";
  private static String GET_LEDUP = "dataupdate.getLedupList";

  private static String INSERT_SUPPORT = "dataupdate.insertsupport";
  private static String GET_SUPPORT = "dataupdate.getsupportList";

  private static String INSERT_SUSPENSION = "dataupdate.insertsuspension";
  private static String GET_SUSPENSION = "dataupdate.getsuspensionList";

  private static String INSERT_PLS = "dataupdate.insertpolelinesegment";
  private static String GET_PLS = "dataupdate.getpolelinesegmentList";

  private static String INSERT_DXD = "dataupdate.insertDXD";
  private static String GET_SUSSEG = "dataupdate.getDXDList";

  public DomainBean getDomain(DomainBean domain)
    throws DataAccessException
  {
    return ((DomainBean)getObject(GET_DOMAIN, domain));
  }

  public void insertPipe(PipeInfoBean pipe) throws DataAccessException
  {
    insert(INSERT_PIPE, pipe);
  }

  public List<PipeInfoBean> getPipelist(PipeInfoBean pipe) throws DataAccessException
  {
    List<PipeInfoBean> list = getObjects(GET_PIPE, pipe);
    for (PipeInfoBean p : list) {
      if (p.getMaintenanceAreaId() != null) {
        DomainBean d = new DomainBean();
        d.setDomainId(p.getMaintenanceAreaId());
        d = (DomainBean)getObject(GET_DOMAIN, d);
        if (d != null) {
          p.setMaintenanceAreaName(d.getDomainName());
        }
      }
    }

    return list;
  }

  public void insertPipeseg(PipeSegmentInfoBean pipeseg) throws DataAccessException
  {
    insert(INSERT_PIPESEG, pipeseg);
  }

  public List<PipeSegmentInfoBean> getPipeseglist(PipeSegmentInfoBean pipeseg) throws DataAccessException
  {
    List<PipeSegmentInfoBean> list = getObjects(GET_PIPESEG, pipeseg);
    for (PipeSegmentInfoBean p : list) {
      if (p.getMaintenanceAreaId() != null) {
        DomainBean d = new DomainBean();
        d.setDomainId(p.getMaintenanceAreaId());
        d = (DomainBean)getObject(GET_DOMAIN, d);
        if (d != null)
          p.setMaintenanceAreaName(d.getDomainName());
      }
      WellInfoBean w;
      PoleInfoBean pole;
      if ((!(p.getStartDeviceType().equals(""))) && (p.getStartDeviceType() != null)) {
        if (p.getStartDeviceType().equals("1")) {
          p.setStartDeviceType("人/手井");
          w = new WellInfoBean();
          w.setWellId(p.getStartDeviceId());
          w = (WellInfoBean)getObject(GET_WELL, w);
          if (w != null)
            p.setStartDeviceName(w.getWellName());
        }
        else if (p.getStartDeviceType().equals("2")) {
          p.setStartDeviceType("电杆");
          pole = new PoleInfoBean();
          pole.setPoleId(p.getStartDeviceId());
          pole = (PoleInfoBean)getObject(GET_POLE, pole);
          if (pole != null) {
            p.setStartDeviceName(pole.getPoleNameSub());
          }
        }
      }
      if ((!(p.getEndDeviceType().equals(""))) && (p.getEndDeviceType() != null)) {
        if (p.getEndDeviceType().equals("1")) {
          p.setEndDeviceType("人/手井");
          w = new WellInfoBean();
          w.setWellId(p.getEndDeviceId());
          w = (WellInfoBean)getObject(GET_WELL, w);
          if (w != null)
            p.setEndDeviceName(w.getWellName());
        }
        else if (p.getEndDeviceType().equals("2")) {
          p.setEndDeviceType("电杆");
          pole = new PoleInfoBean();
          pole.setPoleId(p.getEndDeviceId());
          pole = (PoleInfoBean)getObject(GET_POLE, pole);
          if (pole != null) {
            p.setEndDeviceName(pole.getPoleNameSub());
          }
        }
      }
    }

    return list;
  }

  public void insertPoleline(PolelineInfoBean poleline)
    throws DataAccessException
  {
    insert(INSERT_POLELINE, poleline);
  }

  public List<PolelineInfoBean> getPolelinelist(PolelineInfoBean poleline)
    throws DataAccessException
  {
    List<PolelineInfoBean> list = getObjects(GET_POLELINE, poleline);
    for (PolelineInfoBean p : list) {
      if (p.getMaintenanceAreaId() != null) {
        DomainBean d = new DomainBean();
        d.setDomainId(p.getMaintenanceAreaId());
        d = (DomainBean)getObject(GET_DOMAIN, d);
        if (d != null) {
          p.setMaintenanceAreaName(d.getDomainName());
        }
      }
    }
    return list;
  }

  public WellInfoBean getWell(WellInfoBean well)
    throws DataAccessException
  {
    return ((WellInfoBean)getObject(GET_WELL, well));
  }

  public PoleInfoBean getPole(PoleInfoBean pole) throws DataAccessException
  {
    return ((PoleInfoBean)getObject(GET_POLE, pole));
  }

  public PipeSegmentInfoBean getPipeseg(PipeSegmentInfoBean pipeseg)
    throws DataAccessException
  {
    return ((PipeSegmentInfoBean)getObject(GET_PIPESEG, pipeseg));
  }

  public TubeInfoBean getTube(TubeInfoBean tube)
    throws DataAccessException
  {
    return ((TubeInfoBean)getObject(GET_TUBE, tube));
  }

  public List<TubeInfoBean> getTubelist(TubeInfoBean tube)
    throws DataAccessException
  {
    List<TubeInfoBean> list = getObjects(GET_TUBE, tube);
    PipeSegmentInfoBean p = new PipeSegmentInfoBean();
    List<TubeInfoBean> listson = new ArrayList();
    for (TubeInfoBean t : list) {
      p = new PipeSegmentInfoBean();
      if ((t.getPipeSegmentId() != null) && (!(t.getPipeSegmentId().equals("")))) {
        p.setPipeSegmentId(Integer.valueOf(Integer.parseInt(t.getPipeSegmentId())));
        p = (PipeSegmentInfoBean)getObject(GET_PIPESEG, p);
        if (p != null) {
          t.setPipeSegmentName(p.getPipeSegmentName());
        }
      }
      TubeInfoBean tubeson = new TubeInfoBean();
      tubeson.setFatherPoreId(t.getTubeId());
      listson = getObjects(GET_TUBE, tubeson);
      if (listson.size() > 0) {
        for (TubeInfoBean ts : listson) {
          ts.setFatherPoreName(t.getPipeSegmentName() + "/" + t.getInPipesegCode());
          p = new PipeSegmentInfoBean();
          if ((ts.getPipeSegmentId() != null) && (!(ts.getPipeSegmentId().equals("")))) {
            p.setPipeSegmentId(Integer.valueOf(Integer.parseInt(ts.getPipeSegmentId())));
            p = (PipeSegmentInfoBean)getObject(GET_PIPESEG, p);
            if (p != null) {
              ts.setPipeSegmentName(p.getPipeSegmentName());
            }
          }
        }
      }
    }
    list.addAll(listson);
    return list;
  }

  public void insertTube(TubeInfoBean tube) throws DataAccessException
  {
    insert(INSERT_TUBE, tube);
  }

  public void insertPole(PoleInfoBean poleInfoBean)
    throws DataAccessException
  {
    insert(INSERT_POLE, poleInfoBean);
  }

  public List<PoleInfoBean> getPolelist(PoleInfoBean pole)
    throws DataAccessException
  {
    List<PoleInfoBean> list = getObjects(GET_POLE, pole);
    for (PoleInfoBean p : list) {
      if (p.getMaintenanceAreaId() != null) {
        DomainBean d = new DomainBean();
        d.setDomainId(p.getMaintenanceAreaId());
        d = (DomainBean)getObject(GET_DOMAIN, d);
        if (d != null) {
          p.setMaintenanceAreaName(d.getDomainName());
        }
      }
    }
    return list;
  }

  public void insertWell(WellInfoBean wellInfoBean)
    throws DataAccessException
  {
    insert(INSERT_WELL, wellInfoBean);
  }

  public List<WellInfoBean> getWelllist(WellInfoBean well)
    throws DataAccessException
  {
    List<WellInfoBean> list = getObjects(GET_WELL, well);

    return list;
  }

  public void insertLedup(LedupInfoBean ledupInfoBean)
    throws DataAccessException
  {
    insert(INSERT_LEDUP, ledupInfoBean);
  }

  public List<LedupInfoBean> getLeduplist(LedupInfoBean ledup)
    throws DataAccessException
  {
    List<LedupInfoBean> list = getObjects(GET_LEDUP, ledup);
    for (LedupInfoBean p : list) {
      if (p.getRegion() != null) {
        DomainBean d = new DomainBean();
        d.setDomainId(Integer.valueOf(Integer.parseInt(p.getRegion())));
        d = (DomainBean)getObject(GET_DOMAIN, d);
        if (d != null) {
          p.setMaintenanceAreaName(d.getDomainName());
        }
      }
      if (p.getPoleId() != null) {
        PoleInfoBean pol = new PoleInfoBean();
        pol.setPoleId(p.getPoleId());
        pol = (PoleInfoBean)getObject(GET_POLE, pol);
        if ((pol != null) && 
          (pol.getPoleNameSub() != null)) {
          p.setPoleName(pol.getPoleNameSub());
        }
      }

      if (p.getWellId() != null) {
        WellInfoBean wel = new WellInfoBean();
        wel.setWellId(p.getWellId());
        wel = (WellInfoBean)getObject(GET_WELL, wel);
        if ((wel == null) || 
          (wel.getWellNameSub() == null)) continue;
        p.setWellName(wel.getWellNameSub());
      }

    }

    return list;
  }

  public void insertsupport(SupportInfoBean support) throws DataAccessException
  {
    insert(INSERT_SUPPORT, support);
  }

  public List<SupportInfoBean> getSupportlist(SupportInfoBean support)
    throws DataAccessException
  {
    List<SupportInfoBean> list = getObjects(GET_SUPPORT, support);
    for (SupportInfoBean p : list) {
      if (p.getRegionId() != null) {
        DomainBean d = new DomainBean();
        d.setDomainId(p.getRegionId());
        d = (DomainBean)getObject(GET_DOMAIN, d);
        if (d != null) {
          p.setRegion(d.getDomainName());
        }
      }
    }

    return list;
  }

  public void insertsuspension(SuspensionWireInfoBean suspension) throws DataAccessException
  {
    insert(INSERT_SUSPENSION, suspension);
  }

  public List<SuspensionWireInfoBean> getSuspensionlist(SuspensionWireInfoBean suspension)
    throws DataAccessException
  {
    List<SuspensionWireInfoBean> list = getObjects(GET_SUSPENSION, suspension);
    for (SuspensionWireInfoBean p : list) {
      if (p.getRegionId() != null) {
        DomainBean d = new DomainBean();
        d.setDomainId(p.getRegionId());
        d = (DomainBean)getObject(GET_DOMAIN, d);
        if (d != null) {
          p.setRegion(d.getDomainName());
        }
      }
      if (p.getPoleLineId() != null) {
        PolelineInfoBean poleline = new PolelineInfoBean();
        poleline.setPoleLineId(p.getPoleLineId());
        poleline = (PolelineInfoBean)getObject(GET_POLELINE, poleline);
        if (poleline != null) {
          p.setPoleLineName(poleline.getPoleLineName());
        }
      }

    }

    return list;
  }

  public void insertPoleLS(PolelineSegmentInfoBean polelinesegment) throws DataAccessException
  {
    insert(INSERT_PLS, polelinesegment);
  }

  public List<PolelineSegmentInfoBean> getpoleLineSegmentlist(PolelineSegmentInfoBean polelinesegment) throws DataAccessException
  {
    List<PolelineSegmentInfoBean> list = getObjects(GET_PLS, polelinesegment);
    for (PolelineSegmentInfoBean p : list) {
      if (p.getMaintenanceAreaId() != null) {
        DomainBean d = new DomainBean();
        d.setDomainId(p.getMaintenanceAreaId());
        d = (DomainBean)getObject(GET_DOMAIN, d);
        if (d != null)
          p.setMaintenanceAreaName(d.getDomainName());
      }
      PoleInfoBean pol;
      if (p.getStartDeviceId() != null) {
        pol = new PoleInfoBean();
        pol.setPoleId(p.getStartDeviceId());
        pol = (PoleInfoBean)getObject(GET_POLE, pol);
        if (pol != null) {
          p.setStartDeviceName(pol.getPoleName());
        }
      }
      if (p.getEndDeviceId() != null) {
        pol = new PoleInfoBean();
        pol.setPoleId(p.getEndDeviceId());
        pol = (PoleInfoBean)getObject(GET_POLE, pol);
        if (pol != null) {
          p.setEndDeviceName(pol.getPoleName());
        }
      }

    }

    return list;
  }

  public void insertsuspensionseg(SuspensionWireSegInfoBean suspensionwireSeg)
    throws DataAccessException
  {
    insert(INSERT_DXD, suspensionwireSeg);
  }

  public List<SuspensionWireSegInfoBean> getSuspensionWireSeglist(SuspensionWireSegInfoBean suspensionwireSeg)
    throws DataAccessException
  {
    List<SuspensionWireSegInfoBean> list = getObjects(GET_SUSSEG, suspensionwireSeg);
    for (SuspensionWireSegInfoBean p : list) {
      if (p.getRegionId() != null) {
        DomainBean d = new DomainBean();
        d.setDomainId(p.getRegionId());
        d = (DomainBean)getObject(GET_DOMAIN, d);
        if (d != null) {
          p.setRegion(d.getDomainName());
        }
      }
      if (p.getSuspensionWireId() != null) {
        SuspensionWireInfoBean ss = new SuspensionWireInfoBean();
        ss.setSuspensionWireId(p.getSuspensionWireId());
        ss = (SuspensionWireInfoBean)getObject(GET_SUSPENSION, ss);
        if (ss != null)
          p.setSuspensionWireName(ss.getSuspensionWireName());
      }
      PoleInfoBean pol;
      if ((p.getStartType().equals("1")) && 
        (p.getStartId() != null)) {
        pol = new PoleInfoBean();
        pol.setPoleId(p.getStartId());
        pol = (PoleInfoBean)getObject(GET_POLE, pol);
        if (pol != null) {
          p.setStartName(pol.getPoleName());
        }
      }

      if ((p.getStartType().equals("2")) && 
        (p.getStartId() != null)) {
    	  SupportInfoBean sup = new SupportInfoBean();
        sup.setSupportId(p.getStartId());
        sup = (SupportInfoBean)getObject(GET_SUPPORT, sup);
        if (sup != null) {
          p.setStartName(sup.getSupportName());
        }
      }

      if ((p.getEndType().equals("1")) && 
        (p.getEndId() != null)) {
    	  PoleInfoBean sup = new PoleInfoBean();
        sup.setPoleId(p.getEndId());
        sup = (PoleInfoBean)getObject(GET_POLE, sup);
        if (sup != null) {
          p.setEndName(sup.getPoleName());
        }
      }

      if ((!(p.getEndType().equals("2"))) || 
        (p.getEndId() == null)) continue;
      SupportInfoBean sup = new SupportInfoBean();
      sup.setSupportId(p.getEndId());
      sup = (SupportInfoBean)getObject(GET_SUPPORT, sup);
      if (sup != null) {
        p.setEndName(sup.getSupportName());
      }

    }

    return list;
  }

  public PolelineInfoBean getPoleline(PolelineInfoBean poleline)
    throws DataAccessException
  {
    return ((PolelineInfoBean)getObject(GET_POLELINE, poleline));
  }

  public SupportInfoBean getSupport(SupportInfoBean support)
    throws DataAccessException
  {
    return ((SupportInfoBean)getObject(GET_SUPPORT, support));
  }

  public SuspensionWireInfoBean getSuspension(SuspensionWireInfoBean suspension)
    throws DataAccessException
  {
    return ((SuspensionWireInfoBean)getObject(GET_SUSPENSION, suspension));
  }
}